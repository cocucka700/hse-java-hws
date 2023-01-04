import Models.ConsoleColor;
import Models.CycleException;
import Models.UnknownFileException;

import Services.Aggregator;
import Services.FileReader;
import Services.CyclesFinder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Scanner;

public class Main {
    /**
     * Константная строка, содержащая информацию о некорректно заданном пути до корневой папки
     */
    private static final String INVALID_ROOT_MESSAGE = ConsoleColor.ANSI_RED +
            "Ошибка! Некорректный путь корневой папки." + ConsoleColor.ANSI_RESET +
            " Укажите путь до корневой папки еще раз: ";

    /**
     * Удаление кавычек из абсолютного пути до корневой папки программы (изначально Windows копирует путь с кавычками)
     * @param path абсолюьный путь до папки с кавычками
     * @return файл, представляющий собой корневую папку программы
     */
    private static @NotNull File removeCommas(@NotNull String path) {
        if (path.charAt(0) == '"' && path.charAt(path.length() - 1) == '"') {
            path = path.substring(1, path.length() - 1);
        }
        return new File(path);
    }

    public static void main(String[] args) {
        File root;
        Scanner in = new Scanner(System.in);

        System.out.print("Укажите путь до корневой папки: ");
        do {
            root = removeCommas(in.nextLine());
            if (!root.exists() || !root.isDirectory()) {
                System.out.print(INVALID_ROOT_MESSAGE);
            }
        } while (!root.exists() || !root.isDirectory());

        FileReader fr = new FileReader(root);
        fr.readAllFiles(root);
        CyclesFinder cf = new CyclesFinder();
        try {
            var map = fr.getFILENAME_AND_REQUIRES();
            cf.findCyclesInMap(map);
            Aggregator ag = new Aggregator(fr.getFILENAME_AND_CONTENT(), map);
            ag.sortFilesAndPrint();
            ag.writeResultToFile();
        } catch (CycleException | UnknownFileException e) {
            System.out.println(e.getMessage());
        }
    }
}
