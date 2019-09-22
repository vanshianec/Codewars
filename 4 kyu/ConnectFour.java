//Connect Four
//Take a look at wiki description of Connect Four game:
//
//https://en.wikipedia.org/wiki/Connect_Four
//
//The grid is 6 row by 7 columns, those being named from A to G.
//
//You will receive a list of strings showing the order of the pieces which dropped in columns:
//
//{
//  "A_Red",
//  "B_Yellow",
//  "A_Red",
//  "B_Yellow",
//  "A_Red",
//  "B_Yellow",
//  "G_Red",
//  "B_Yellow"
//}
//The list may contain up to 42 moves and shows the order the players are playing.
//
//The first player who connects four items of the same color is the winner.
//
//You should return "Yellow", "Red" or "Draw" accordingly.



import java.util.*;

public class ConnectFour {

    static int[][] board;

    public static String whoIsWinner(List<String> piecesPositionList) {
        System.out.println(String.join(" ", piecesPositionList));
        int boardHeight = 6;
        int boardWidth = 7;
        board = new int[boardHeight][boardWidth];
        int[] rowPosition = new int[boardWidth];
        for (int i = 0; i < boardWidth; i++) {
            rowPosition[i] = boardHeight - 1;
        }

        while (!piecesPositionList.isEmpty()) {
            String currentMove = piecesPositionList.remove(0);
            int col = getColumn(currentMove.split("_")[0]);
            int color = getColorId(currentMove.split("_")[1]);
            int row = rowPosition[col]--;
            board[row][col] = color;
            if (wins(row, col, color)) {
                return getColorFromId(color);
            }
        }
        return "Draw";
    }

    private static String getColorFromId(int id) {
        return id == 2 ? "Red" : "Yellow";
    }

    private static int getColorId(String color) {
        return color.equals("Red") ? 2 : 1;
    }

    private static int getColumn(String letter) {
        switch (letter) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            case "E":
                return 4;
            case "F":
                return 5;
        }
        return 6;
    }

    private static boolean wins(int row, int col, int color) {
        return connectedVertical(row, col, color) || connectedHorizontal(row, col, color) || connectedDiagonal(row, col, color);
    }

    private static boolean connectedVertical(int row, int col, int color) {
        for (int i = 1; i < 4; i++) {
            if (row + i == board.length) {
                break;
            }
            if (board[row + i][col] != color) {
                break;
            }
            if (i == 3) {
                return true;
            }
        }

        return false;
    }

    private static boolean connectedHorizontal(int row, int col, int color) {

        int currentCount = 0;
        for (int i = Math.max(0, col - 3); i <= Math.min(col + 3, board[0].length - 1) - 1; i++) {
            if (board[row][i] == color && board[row][i] == board[row][i + 1]) {
                currentCount++;
                if (currentCount == 3) {
                    return true;
                }
            } else {
                currentCount = 0;
            }
        }
        return false;
    }

    private static boolean connectedDiagonal(int row, int col, int color) {

        //set starting bottom-left position
        int startingRow = row;
        int startingCol = col;
        while (startingCol > 0 && startingRow < board.length - 1) {
            startingCol--;
            startingRow++;
        }

        //from bottom-left to top-right
        int currentCount = 0;
        while (startingRow > 0 && startingCol < board[0].length - 1) {
            if (board[startingRow][startingCol] == color && board[startingRow][startingCol] == board[startingRow - 1][startingCol + 1]) {
                currentCount++;
                if (currentCount == 3) {
                    return true;
                }
            } else {
                currentCount = 0;
            }
            startingRow--;
            startingCol++;
        }

        //set starting bottom-right position
        startingRow = row;
        startingCol = col;
        while (startingCol < board[0].length - 1 && startingRow < board.length - 1) {
            startingCol++;
            startingRow++;
        }

        //from bottom-right to top-left
        currentCount = 0;
        while (startingRow > 0 && startingCol > 0) {
            if (board[startingRow][startingCol] == color && board[startingRow][startingCol] == board[startingRow - 1][startingCol - 1]) {
                currentCount++;
                if (currentCount == 3) {
                    return true;
                }
            } else {
                currentCount = 0;
            }
            startingRow--;
            startingCol--;
        }

        return false;
    }
}