//You are at position [0, 0] in maze NxN and you can only move in one of the four cardinal directions (i.e. North, East, South, West). Return true if you can reach position [N-1, N-1] or false otherwise.
//
//Empty positions are marked .. Walls are marked W. Start and exit positions are empty in all test cases.

public class Finder {

    static char[][] matrix;

    static boolean pathFinder(String maze) {

        int n = getLength(maze);
        matrix = new char[n][n];
        fillMatrix(maze);

        try {
            findPath(0, 0);
        } catch (IllegalArgumentException e) {
            return true;
        }

        return false;
    }

    private static int getLength(String maze) {
        int count = 0;
        for (int i = 0; i < maze.length(); i++) {
            if (maze.charAt(i) == '\n') {
                break;
            }
            count++;
        }
        return count;
    }

    private static void fillMatrix(String maze) {
        int row = 0;
        int col = 0;

        for (int i = 0; i < maze.length(); i++) {
            if (maze.charAt(i) == '\n') {
                row++;
                col = 0;
            } else {
                matrix[row][col++] = maze.charAt(i);
            }
        }
    }

    private static void findPath(int row, int col) {
        if (isOutOfBounds(matrix, row, col)) {
            return;
        }

        if (hasReachedTheEnd(row, col)) {
            throw new IllegalArgumentException("Found a solution");
        }

        if (isPassable(row, col) && !isMarked(row, col)) {
            mark(row, col);
            findPath(row - 1, col);
            findPath(row, col - 1);
            findPath(row, col + 1);
            findPath(row + 1, col);
        }
    }

    private static boolean isOutOfBounds(char[][] matrix, int row, int col) {
        return row < 0 || col < 0 || row == matrix.length || col == matrix[0].length;
    }

    private static boolean hasReachedTheEnd(int row, int col) {
        return row == matrix.length - 1 && col == matrix[0].length - 1;
    }

    private static boolean isPassable(int row, int col) {
        return matrix[row][col] != 'W';
    }

    private static boolean isMarked(int row, int col) {
        return matrix[row][col] == '*';
    }

    private static void mark(int row, int col) {
        matrix[row][col] = '*';
    }

}