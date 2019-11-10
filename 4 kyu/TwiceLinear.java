//Consider a sequence u where u is defined as follows:
//
//The number u(0) = 1 is the first one in u.
//For each x in u, then y = 2 * x + 1 and z = 3 * x + 1 must be in u too.
//There are no other numbers in u.
//Ex: u = [1, 3, 4, 7, 9, 10, 13, 15, 19, 21, 22, 27, ...]
//
//1 gives 3 and 4, then 3 gives 7 and 10, 4 gives 9 and 13, then 7 gives 15 and 22 and so on...
//
//Task:
//Given parameter n the function dbl_linear (or dblLinear...) returns the element u(n) of the ordered (with <) sequence u (so, there are no duplicates).
//
//Example:
//dbl_linear(10) should return 22
//
//Note:
//Focus attention on efficiency

import java.util.Arrays;

class DoubleLinear {

    public static int dblLinear(int n) {
        int[] u = new int[2 * n + 1];
        int count = 0;
        u[0] = 1;

        for (int i = 0; i < n; i++) {
            int x = u[i] * 2 + 1;
            insertSorted(u, x, i + 1 + count);
            int y = u[i] * 3 + 1;
            insertSorted(u, y, i + 2 + count);
            count++;
        }

        u = Arrays.stream(u).distinct().toArray();

        return u[count];

    }

    private static void insertSorted(int[] u, int value, int index) {

        while (--index >= 0 && u[index] > value) {
            u[index + 1] = u[index];
        }

        u[index + 1] = value;
    }
}