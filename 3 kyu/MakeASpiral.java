//Your task, is to create a NxN spiral with a given size.
//
//For example, spiral with size 5 should look like this:
//
//00000
//....0
//000.0
//0...0
//00000
//and with the size 10:
//
//0000000000
//.........0
//00000000.0
//0......0.0
//0.0000.0.0
//0.0..0.0.0
//0.0....0.0
//0.000000.0
//0........0
//0000000000
//Return value should contain array of arrays, of 0 and 1, for example for given size 5 result should be:
//
//[[1,1,1,1,1],[0,0,0,0,1],[1,1,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
//Because of the edge-cases for tiny spirals, the size will be at least 5.
//
//General rule-of-a-thumb is, that the snake made with '1' cannot touch to itself.

import java.util.ArrayList;
import java.util.List;

public class MakeASpiral {

    static int[][] matrix;
    static List<String> moves = new ArrayList<>();

    public static int[][] spiralize(int size) {
        moves.add("Right");
        moves.add("Down");
        moves.add("Left");
        moves.add("Up");
        matrix = new int[size][size];
        try {
            fillSpiral(0, 0);
        } catch (Exception ignore) {
            ;
        }
        return matrix;
    }

    private static void fillSpiral(int row, int col) throws Exception {
        if (isOutOfBounds(row, col) || !isPassable(row, col)) {
            String move = moves.remove(0);
            moves.add(moves.size(), move);
            return;
        }

        mark(row, col);

        for (int i = 0; i < matrix.length / 5 + 1; i++) {
            if (moves.get(0).equals("Right")) {
                fillSpiral(row, col + 1);
            }

            if (moves.get(0).equals("Down")) {
                fillSpiral(row + 1, col);
            }

            if (moves.get(0).equals("Left")) {
                fillSpiral(row, col - 1);
            }

            if (moves.get(0).equals("Up")) {
                fillSpiral(row - 1, col);
            }

            //end of spiral no need to go back in the recursion so we throw an exception to stop it
            if (i == matrix.length / 5) {
                throw new Exception();
            }
        }
    }

    private static void mark(int row, int col) {
        matrix[row][col] = 1;
    }

    private static boolean isPassable(int row, int col) {
        int markedNeighboursCount = 0;
        if (!isOutOfBounds(row - 1, col) && matrix[row - 1][col] == 1) {
            markedNeighboursCount++;
        }
        if (!isOutOfBounds(row + 1, col) && matrix[row + 1][col] == 1) {
            markedNeighboursCount++;
        }
        if (!isOutOfBounds(row, col - 1) && matrix[row][col - 1] == 1) {
            markedNeighboursCount++;
        }
        if (!isOutOfBounds(row, col + 1) && matrix[row][col + 1] == 1) {
            markedNeighboursCount++;
        }
        return markedNeighboursCount < 2;
    }

    private static boolean isOutOfBounds(int row, int col) {
        return row < 0 || col < 0 || row >= matrix.length || col >= matrix[0].length;
    }
}
