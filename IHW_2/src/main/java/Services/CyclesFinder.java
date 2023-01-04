package Services;

import Models.ConsoleColor;
import Models.CycleException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CyclesFinder {
    /**
     * Поиск циклов в словаре с названиями файлов и их зависимостями
     * @param fileNameAndRequires словарь с названиями файлов и их зависимостями
     */
    public void findCyclesInMap(@NotNull Map<String, List<String>> fileNameAndRequires) throws CycleException {
        for (String fileName : fileNameAndRequires.keySet()) {
            List<String> visited = new ArrayList<>();
            if (findCyclesInFile(fileName, fileNameAndRequires, visited)) {
                visited.add(fileName);
                printCycle(visited);
                throw new CycleException(ConsoleColor.ANSI_RED + "Ошибка! Обнаружен цикл: " +
                        ConsoleColor.ANSI_RESET + printCycle(visited));
            }
        }
        System.out.println("\n" + ConsoleColor.ANSI_GREEN + "Циклов нет!" + ConsoleColor.ANSI_RESET);
    }

    /**
     * Поиск циклов в зависимостях опредленного файла
     * @param fileName имя файла, в котором производится поиск циклов
     * @param fileNameAndRequires словарь, содержащий путь до файла в рамках корневой папки и список файлов, от которых он зависит
     * @param visited список посещенных файлов
     * @return true, если цикл найден, иначе false
     */
    private boolean findCyclesInFile(@NotNull String fileName, @NotNull Map<String, List<String>> fileNameAndRequires, @NotNull List<String> visited) {
        if (visited.contains(fileName)) {
            return true;
        }
        visited.add(fileName);
        for (String requiredFileName : fileNameAndRequires.get(fileName)) {
            if (findCyclesInFile(requiredFileName, fileNameAndRequires, visited)) {
                return true;
            }
        }
        visited.remove(fileName);
        return false;
    }

    /**
     * Вывод цикла в консоль
     * @param visited список файлов, зависимости в которых представляют цикл
     * @return строка, представляющая цикл
     */
    private static String printCycle(List<String> visited) {
        StringBuilder cycle = new StringBuilder();
        for (int i = 0; i < visited.size(); ++i) {
            if (i == visited.size() - 1) {
                cycle.append(ConsoleColor.ANSI_PURPLE).append(visited.get(i)).append(ConsoleColor.ANSI_RESET);
            } else {
                cycle.append(ConsoleColor.ANSI_PURPLE).append(visited.get(i)).append(ConsoleColor.ANSI_RED).append(" -> ").append(ConsoleColor.ANSI_RESET);
            }
        }
        return cycle.toString();
    }
}
