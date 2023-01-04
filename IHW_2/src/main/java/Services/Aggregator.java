package Services;

import Models.ConsoleColor;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Aggregator {
    /**
     * Словарь, представлющий собой название файла и его содержимое
     */
    private final Map <String, List<String>> FILENAME_AND_CONTENT;
    /**
     * Словарь, представлющий собой название файла и список файлов, от которых он зависит
     */
    private final Map <String, List<String>> FILENAME_AND_REQUIRES;
    /**
     * Список, представлющий собой содержимое файлов, которые были топологически отсортированы
     */
    private final List<String> text = new ArrayList<>();

    /**
     * Конструктор класса
     * @param filenameAndContent словарь с названиями файлов и их содержимым
     * @param filenameAndRequirements словар с названиями файлов и их зависимостями
     */
    public Aggregator(Map<String, List<String>> filenameAndContent, Map<String, List<String>> filenameAndRequirements) {
        FILENAME_AND_CONTENT = filenameAndContent;
        FILENAME_AND_REQUIRES = filenameAndRequirements;
    }

    /**
     * Топологическая сортировка словаря, в котором записаны названия файлов и их содержимое в список, представляющий собой
     * содержимое этих файлов в топологически отсортированном порядке
     */
    public void sortFilesAndPrint() {
        List<String> files = new ArrayList<>(FILENAME_AND_REQUIRES.keySet());
        while (!files.isEmpty()) {
            String file = files.get(0);
            boolean isAdded = false;
            files.remove(0);
            if (FILENAME_AND_REQUIRES.get(file).isEmpty()) {
                text.addAll(FILENAME_AND_CONTENT.get(file));
            } else {
                for (String require : FILENAME_AND_REQUIRES.get(file)) {
                    if (files.contains(require)) {
                        files.add(file);
                        break;
                    } else if (!isAdded){
                        text.addAll(FILENAME_AND_CONTENT.get(file));
                        isAdded = true;
                    }
                }
            }
        }
        System.out.println("\n" + ConsoleColor.ANSI_GREEN + "Файлы были успешно отсортированы:" + ConsoleColor.ANSI_RESET);
        for (String line : text) {
            System.out.println(line);
        }
    }

    /**
     * Запись содержимого файлов в файл result.txt
     */
    public void writeResultToFile() {
        char separator = File.separatorChar;
        String path = "src" + separator + "main" + separator + "result.txt";
        try (FileWriter writer = new FileWriter(path, false)) {
            for (String line : text) {
                writer.write(line + "\n");
            }
            writer.flush();
            System.out.println("\n" + ConsoleColor.ANSI_GREEN + "Результат записан в файл " + ConsoleColor.ANSI_PURPLE + path + ConsoleColor.ANSI_RESET);
        } catch (Exception e) {
            System.out.println("\n" + ConsoleColor.ANSI_RED + "Ошибка при записи в файл!" + ConsoleColor.ANSI_RESET);
        }
    }
}
