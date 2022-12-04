package GameMods;

import Models.Cell;
import Models.Field;

import java.util.List;
import java.util.Scanner;

public class BotGame extends Game{
    public static void start(Scanner in) {
        Field f = new Field();
        while (hasWinner) {

        }
        char winner = 'g';
        switch (winner) {
            case 'g' -> System.out.println("Вы победили!");
            case 'r' -> System.out.println("Вы проиграли!");
            default -> System.out.println("Ничья!");
        }
    }


    @Override
    Cell getValuableCell() {
        return null;
    }
}
