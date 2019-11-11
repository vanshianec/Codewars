//Sudoku Background
//Sudoku is a game played on a 9x9 grid. The goal of the game is to fill all cells of the grid with digits from 1 to 9, so that each column, each row, and each of the nine 3x3 sub-grids (also known as blocks) contain all of the digits from 1 to 9.
//(More info at: http://en.wikipedia.org/wiki/Sudoku)
//
//Sudoku Solution Validator
//Write a function validSolution/ValidateSolution/valid_solution() that accepts a 2D array representing a Sudoku board, and returns true if it is a valid solution, or false otherwise. The cells of the sudoku board may also contain 0's, which will represent empty cells. Boards containing one or more zeroes are considered to be invalid solutions.
//
//The board is always 9 cells by 9 cells, and every cell only contains integers from 0 to 9.
//
//Examples
//validSolution([
//  [5, 3, 4, 6, 7, 8, 9, 1, 2],
//  [6, 7, 2, 1, 9, 5, 3, 4, 8],
//  [1, 9, 8, 3, 4, 2, 5, 6, 7],
//  [8, 5, 9, 7, 6, 1, 4, 2, 3],
//  [4, 2, 6, 8, 5, 3, 7, 9, 1],
//  [7, 1, 3, 9, 2, 4, 8, 5, 6],
//  [9, 6, 1, 5, 3, 7, 2, 8, 4],
//  [2, 8, 7, 4, 1, 9, 6, 3, 5],
//  [3, 4, 5, 2, 8, 6, 1, 7, 9]
//]); // => true
//validSolution([
//  [5, 3, 4, 6, 7, 8, 9, 1, 2], 
//  [6, 7, 2, 1, 9, 0, 3, 4, 8],
//  [1, 0, 0, 3, 4, 2, 5, 6, 0],
//  [8, 5, 9, 7, 6, 1, 0, 2, 0],
//  [4, 2, 6, 8, 5, 3, 7, 9, 1],
//  [7, 1, 3, 9, 2, 4, 8, 5, 6],
//  [9, 0, 1, 5, 3, 7, 2, 1, 4],
//  [2, 8, 7, 4, 1, 9, 6, 3, 5],
//  [3, 0, 0, 4, 8, 1, 1, 7, 9]
//]); // => false

import java.util.Arrays;

public class SudokuValidator {
    public static boolean check(int[][] sudoku) {

        int[] validNumbers = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

        boolean validRows = areRowsValid(sudoku, validNumbers);
        boolean validCols = areColumnsValid(sudoku, validNumbers);
        boolean validSubGrids = areSubGridsValid(sudoku, validNumbers);
        return validRows && validCols && validSubGrids;
    }

    private static boolean areRowsValid(int[][] sudoku, int[] validNumbers) {

        for (int i = 0; i < sudoku.length; i++) {

            int[] row = new int[sudoku.length];
            for (int j = 0; j < sudoku[0].length; j++) {
                row[j] = sudoku[i][j];
            }

            Arrays.sort(row);

            if (!areEqual(row, validNumbers)) {
                return false;
            }
        }

        return true;
    }

    private static boolean areColumnsValid(int[][] sudoku, int[] validNumbers) {

        for (int i = 0; i < sudoku[0].length; i++) {

            int[] col = new int[sudoku[0].length];
            for (int j = 0; j < sudoku.length; j++) {
                col[j] = sudoku[j][i];
            }

            Arrays.sort(col);

            if (!areEqual(col, validNumbers)) {
                return false;
            }
        }

        return true;
    }

    private static boolean areSubGridsValid(int[][] sudoku, int[] validNumbers) {

        for (int i = 0; i < sudoku.length; i += 3) {

            for (int j = 0; j < sudoku[0].length; j += 3) {
                int[] subGrid = new int[sudoku.length];
                int index = 0;
                for (int row = i; row < i + 3; row++) {
                    for (int col = j; col < j + 3; col++) {
                        subGrid[index++] = sudoku[row][col];
                    }
                }

                Arrays.sort(subGrid);

                if (!areEqual(subGrid, validNumbers)) {
                    return false;
                }

            }
        }

        return true;
    }

    private static boolean areEqual(int[] current, int[] valid) {

        for (int i = 0; i < valid.length; i++) {
            if (current[i] != valid[i]) {
                return false;
            }
        }

        return true;
    }


}