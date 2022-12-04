package Models;

import Enums.CellPosition;

public class Cell {
    private char color;
    private final int x;
    private final int y;
    private final CellPosition cellPos;

    Cell(int x, int y, char color, CellPosition cellPos) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.cellPos = cellPos;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }

    public CellPosition getCellPos() {
        return cellPos;
    }

    @Override
    public String toString() {
        if (color == '0') {
            return "■";
        }
        return switch (color) {
            case 'g' -> ConsoleColor.GREEN + "■" + ConsoleColor.DEFAULT;
            case 'r' -> ConsoleColor.RED + "■" + ConsoleColor.DEFAULT;
            default -> ConsoleColor.CYAN + "■" + ConsoleColor.DEFAULT;
        };
    }
}
