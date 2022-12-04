package GameMods;

import Models.Cell;
import Models.Field;

import java.util.HashMap;
import java.util.Map;

public abstract class Game {
    Field field = new Field();

    public void getAllPossibleMoves(char color) {
        Map<Cell, Integer> available = new HashMap<>();
        for (int i = 0; i < field.getSize(); ++i) {
            for (int j = 0; j < field.getSize(); ++j) {
                Cell current = field.at(i, j);
                if (current.getColor() != '0') {
                    available.putAll(getMovesForCell(current));
                }
            }
        }

        if (available.isEmpty()) {
            System.out.println("Доступных клеток нет!");
        } else {
            String output = "Список доступных клеток: ";
            int count = 1;
            for (Cell current : available.keySet()) {
                if (count != available.size()) {
                    output += String.format("%d. [%d, %d], Захвачено фишек - %d;\n", count, current.getX(), current.getY(), available.get(current));
                } else {
                    output += String.format("%d. [%d, %d]; Захвачено фишек - %d.", count, current.getX(), current.getY(), available.get(current));
                }
            ++count;
            }
            System.out.println(output);
        }
    }

    public HashMap<Cell, Integer> getMovesForCell(Cell cell) {

    }

    public void makeTurn(int x, int y, char color) {
        field.at(x - 1, y - 1).setColor(color);
        field.setTakenCells(field.getTakenCells() + 1);
        if (color == 'g') {
            field.setGreenCells(field.getGreenCells() + 1);
        } else {
            field.setRedCells(field.getRedCells() + 1);
        }
    }

    public char getWinner() {
        if (field.getRedCells() > field.getGreenCells())  {
            return 'r';
        } else if (field.getGreenCells() > field.getRedCells()) {
            return 'g';
        } else {
            return '0';
        }
    }

    abstract Cell getValuableCell();
}
