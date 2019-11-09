//Snail Sort
//Given an n x n array, return the array elements arranged from outermost elements to the middle element, traveling clockwise.
//
//array = [[1,2,3],
//         [4,5,6],
//         [7,8,9]]
//snail(array) #=> [1,2,3,6,9,8,7,4,5]
//For better understanding, please follow the numbers of the next array consecutively:
//
//array = [[1,2,3],
//         [8,9,4],
//         [7,6,5]]
//snail(array) #=> [1,2,3,4,5,6,7,8,9]

//NOTE: The idea is not sort the elements from the lowest value to the highest; the idea is to traverse the 2-d array in a clockwise snailshell pattern.
//
//NOTE 2: The 0x0 (empty matrix) is represented as en empty array inside an array [[]].

public class Snail {

    public static int[] snail(int[][] array) {

        int index = 0, row = 0, col = 0, n = array.length;

        if (array[0].length == 0) {
            return new int[0];
        }

        int result[] = new int[n * n];

        int cyclesCount = n % 2 == 0 ? n / 2 : n / 2 + 1;

        for (int cycles = 0; cycles < cyclesCount; cycles++) {

            for (int i = col; i < n - col; i++) {
                result[index++] = array[row][i];
            }

            for (int i = row + 1; i < n - row; i++) {
                result[index++] = array[i][n - 1 - col];
            }

            for (int i = n - 2 - col; i >= col; i--) {
                result[index++] = array[n - 1 - row][i];
            }

            for (int i = n - 2 - row; i >= row + 1; i--) {
                result[index++] = array[i][col];
            }

            row++;
            col++;
        }

        return result;
    }
}