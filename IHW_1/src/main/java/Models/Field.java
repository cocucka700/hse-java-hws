package Models;

import Enums.CellPosition;

import java.util.*;

public class Field {
    private final static int size = 8;
    private int takenCells = 4;
    private int greenCells = 2;
    private int redCells = 2;
    private final Cell[][] field = new Cell[size][size];

    public Field() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (((i == 0 || i == size - 1) && i == j) || ((i == size - 1 && j == 0) || i == 0 && j == size - 1)) {
                    field[i][j] = new Cell(i, j, '0', CellPosition.corner);
                } else if (i == 0 || j == 0 || i == size - 1 || j == size - 1) {
                    field[i][j] = new Cell(i, j, '0', CellPosition.edge);
                } else if ((i == 3 && j == 4) || (i == 4 && j == 3)) {
                    field[i][j] = new Cell(i, j, 'g', CellPosition.other);
                } else if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
                    field[i][j] = new Cell(i, j, 'r', CellPosition.other);
                } else {
                    field[i][j] = new Cell(i, j, '0', CellPosition.other);
                }
            }
        }
    }

    public int getSize() {
        return size;
    }

    public int getTakenCells() {
        return takenCells;
    }

    public void setTakenCells(int value) {
        takenCells = value;
    }

    public int getGreenCells() {
        return greenCells;
    }

    public void setGreenCells(int value) { greenCells = value; }

    public int getRedCells() {
        return redCells;
    }

    public void setRedCells(int value) { redCells = value; }

    public void restoreColors() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                Cell current = field[i][j];
                if (current.getColor() == 'c') {
                    current.setColor('0');
                }
            }
        }
    }

    public Cell at(int x, int y) {
        return field[x][y];
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < size + 1; ++i) {
            for (int j = -1; j < size; ++j) {
                if (i == size && j == -1) {
                    out += "  ";
                } else if (i == size) {
                    out += (j + 1) + " ";
                } else if (j == -1) {
                    out += (size - i) + " ";
                } else {
                    out += field[i][j] + " ";
                }
            }
            if (i != size) {
                out += "\n";
            }
        }
        return out;
    }
}
