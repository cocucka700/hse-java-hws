package Services;

import Models.ConsoleColor;
import Models.UnknownFileException;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileReader {
    /**
     * Файл, представляющий собой корневую папку
     */
    private final File ROOT;
    /**
     * Словарь, представлющий собой название файла и его содержимое
     */
    final Map<String, List<String>> FILENAME_AND_CONTENT = new HashMap<>();
    /**
     * Словарь, представлющий собой название файла и список файлов, от которых он зависит
     */
    final Map<String, List<String>> FILENAME_AND_REQUIRES = new HashMap<>();

    /**
     * Конструктор класса
     * @param root файл, представляющий собой корневую папку
     */
    public FileReader(File root) {
        this.ROOT = root;
    }

    /**
     * Рекурсивное чтение всех файлов в заданном каталоге
     * @param dir заданный каталог, содержимое файлов которого будет прочитано
     */
    public void readAllFiles(@NotNull File dir) {
        if (!Files.isReadable(Paths.get(dir.getAbsolutePath()))) {
            System.out.println(ConsoleColor.ANSI_RED + "Ошибка! Каталог " +
                    ConsoleColor.ANSI_PURPLE + dir.getAbsolutePath() +
                    ConsoleColor.ANSI_RED + " закрыт для чтения!" +
                    ConsoleColor.ANSI_RESET);
            return;
        }
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println(ConsoleColor.ANSI_RED + "Каталог " + ConsoleColor.ANSI_PURPLE +
                    dir + ConsoleColor.ANSI_RED +
                    " пуст!" + ConsoleColor.ANSI_RESET);
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                readAllFiles(file);
            } else if (Files.isReadable(Paths.get(file.getAbsolutePath()))) {
                fillFileNameAndContentMapByFile(file);
                fillFileNameAndRequiresMapByFile(file);
            } else {
                System.out.println(ConsoleColor.ANSI_RED + "Ошибка! Файл " +
                        ConsoleColor.ANSI_PURPLE + dir.getAbsolutePath() +
                        ConsoleColor.ANSI_RED + " закрыт для чтения!" +
                        ConsoleColor.ANSI_RESET);
                return;
            }
        }

    }

    /**
     * Добавление в словарь, содержащего путь до файла в рамках корневой папки и содержимого этого файла,
     * нового значения
     * @param file файл, путь и содержимое которого должно быть добавлено в словарь
     */
    void fillFileNameAndContentMapByFile(@NotNull File file) {
        try {
            String fileAbsolutePath = file.getAbsolutePath();
            FILENAME_AND_CONTENT.putIfAbsent(getFilePathInRoot(fileAbsolutePath), Files.readAllLines(Paths.get(fileAbsolutePath)));
        } catch (IOException e) {
            System.out.println(ConsoleColor.ANSI_RED +
                    "Ошибка чтения файла: " + ConsoleColor.ANSI_PURPLE + file.getAbsolutePath() +
                    ConsoleColor.ANSI_RESET);
        }
    }

    /**
     * Поиск в словаре, содержащем имена файлов и их содержимое, наличие зависимостей
     * между файлами и добавление их с соответствующий словарь
     * @param file текущий файл
     */
    void fillFileNameAndRequiresMapByFile(@NotNull File file) {
        String filePath = getFilePathInRoot(file.getAbsolutePath());
        List<String> content = FILENAME_AND_CONTENT.get(filePath);
        List<String> requires = new ArrayList<>();
        for (String line : content) {
            if (line.contains("require")) {
                int index = line.indexOf("require") + "require".length() + 2;
                requires.add(line.substring(index, line.length() - 1) + ".txt");
            }
        }
        FILENAME_AND_REQUIRES.putIfAbsent(filePath, requires);
    }

    /**
     * Проверяет словарь зависимостей на наличие несуществующих файлов в зависимостях
     */
    private void checkRequires() throws UnknownFileException {
        for (Map.Entry<String, List<String>> entry : FILENAME_AND_REQUIRES.entrySet()) {
            for (String filename : entry.getValue()) {
                if (!FILENAME_AND_CONTENT.containsKey(filename)) {
                    throw new UnknownFileException(ConsoleColor.ANSI_RED + "Ошибка! Файл " +
                            ConsoleColor.ANSI_PURPLE + entry.getKey() + ConsoleColor.ANSI_RED +
                            " зависит от файла " + ConsoleColor.ANSI_PURPLE + filename + ConsoleColor.ANSI_RED +
                            " которого не существует!" + ConsoleColor.ANSI_RESET);
                }
            }
        }
    }

    /**
     * Получение псевдоабсолютного пути файла в корневой папки
     * @param absolutePath абсолютный путь файла
     * @return Псевдоабсолютный путь до файла, начинающийся с названия корневой папки
     */
    @NotNull String getFilePathInRoot(String absolutePath) {
        StringBuilder filePath = new StringBuilder();
        String[] splittedAbsolutePath = splitFilePath(absolutePath);
        String[] splittedRoot = splitFilePath(ROOT.getAbsolutePath());
        int index = 0;
        while (!Objects.equals(splittedAbsolutePath[index], splittedRoot[splittedRoot.length - 1])) {
            index++;
        }

        for (int i = index + 1; i < splittedAbsolutePath.length; ++i) {
            if (i != splittedAbsolutePath.length - 1) {
                filePath.append(splittedAbsolutePath[i]).append("\\");
            } else {
                filePath.append(splittedAbsolutePath[i]);
            }
        }
        filePath = new StringBuilder(filePath.toString().replace("\\", "/"));
        return filePath.toString();
    }

    /**
     * Разбиение абсолютного пути файла на поддиректории
     * @param str абсолютный путь файла
     * @return Массив строк, а именно названия каждой поддиректории
     */
    String @NotNull [] splitFilePath(@NotNull String str) {
        List<String> splitted = new ArrayList<>();
        StringBuilder currentPart = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            if (File.separatorChar != str.charAt(i)) {
                currentPart.append(str.charAt(i));
            } else {
                splitted.add(currentPart.toString());
                currentPart.setLength(0);
            }
        }
        if (!currentPart.isEmpty()) {
            splitted.add(currentPart.toString());
        }
        return splitted.toArray(new String[0]);
    }

    /**
     * Получение словаря, в котором ключами являются имена файлов, а значениями - списки строк, содержащиеся в файлах
     * @return Требуемый словарь
     */
    public Map<String, List<String>> getFILENAME_AND_CONTENT() {
        return FILENAME_AND_CONTENT;
    }

    /**
     * Получение словаря, в котором ключами являются имена файлов, а значениями - зависимые файлы
     * @return Требуемый словарь
     */
    public Map<String, List<String>> getFILENAME_AND_REQUIRES() throws UnknownFileException {
        checkRequires();
        return FILENAME_AND_REQUIRES;
    }
}
