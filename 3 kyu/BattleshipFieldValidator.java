//Write a method that takes a field for well-known board game "Battleship" as an argument and returns true if it has a valid disposition of ships, false otherwise. Argument is guaranteed to be 10*10 two-dimension array. Elements in the array are numbers, 0 if the cell is free and 1 if occupied by ship.
//
//Battleship (also Battleships or Sea Battle) is a guessing game for two players. Each player has a 10x10 grid containing several "ships" and objective is to destroy enemy's forces by targetting individual cells on his field. The ship occupies one or more cells in the grid. Size and number of ships may differ from version to version. In this kata we will use Soviet/Russian version of the game.
//
//
//Before the game begins, players set up the board and place the ships accordingly to the following rules:
//There must be single battleship (size of 4 cells), 2 cruisers (size 3), 3 destroyers (size 2) and 4 submarines (size 1). Any additional ships are not allowed, as well as missing ships.
//Each ship must be a straight line, except for submarines, which are just single cell.
//
//The ship cannot overlap or be in contact with any other ship, neither by edge nor by corner.

public class BattleField {

    public static boolean fieldValidator(int[][] field) {
        int shipsCount[] = {4, 3, 2, 1};

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == 1) {
                    if (isSingleShip(i, j, field)) {
                        shipsCount[0]--;
                    } else if (j < field[0].length - 1 && field[i][j + 1] == 1) {
                        if (hasRowNeighbours(i, j, field)) {
                            return false;
                        }
                        int count = 1;
                        for (int k = j + 1; k < field[0].length; k++) {
                            if (field[i][k] == 0) {
                                break;
                            }
                            if (hasRowNeighbours(i, k, field)) {
                                return false;
                            }
                            count++;
                        }
                        if (count > 4) {
                            return false;
                        }
                        shipsCount[count - 1]--;
                        j += count - 1;
                    }
                }
            }
        }

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[j][i] == 1) {
                    if (j < field.length - 1 && field[j + 1][i] == 1) {
                        if (hasColNeighbours(j, i, field)) {
                            return false;
                        }
                        int count = 1;
                        for (int k = j + 1; k < field.length; k++) {
                            if (field[k][i] == 0) {
                                break;
                            }
                            if (hasColNeighbours(k, i, field)) {
                                return false;
                            }
                            count++;
                        }
                        if (count > 4) {
                            return false;
                        }
                        shipsCount[count - 1]--;
                        j += count - 1;
                    }
                }
            }
        }

        for (int i = 0; i < shipsCount.length; i++) {
            if (shipsCount[i] != 0) {
                return false;
            }
        }

        return true;
    }

    private static boolean isSingleShip(int row, int col, int[][] field) {
        return (row == 0 || field[row - 1][col] == 0)
                && (col == field[0].length - 1 || field[row][col + 1] == 0)
                && (row == field.length - 1 || field[row + 1][col] == 0)
                && (col == 0 || field[row][col - 1] == 0);
    }

    private static boolean hasRowNeighbours(int row, int col, int[][] field) {
        for (int i = Math.max(col - 1, 0); i < Math.min(field[0].length, col + 2); i++) {
            if (row > 0) {
                if (field[row - 1][i] == 1) {
                    return true;
                }
            }
            if (row < field.length - 1) {
                if (field[row + 1][i] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasColNeighbours(int row, int col, int[][] field) {
        for (int i = Math.max(row - 1, 0); i < Math.min(field.length, row + 2); i++) {
            if (col > 0) {
                if (field[i][col - 1] == 1) {
                    return true;
                }
            }
            if (col < field[0].length - 1) {
                if (field[i][col + 1] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

}