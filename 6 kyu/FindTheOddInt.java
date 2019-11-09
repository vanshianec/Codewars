//Given an array, find the int that appears an odd number of times.
//
//There will always be only one integer that appears an odd number of times.


import java.util.Arrays;

public class FindOdd {
    public static int findIt(int[] a) {
        Arrays.sort(a);

        int currentOccurences = 1;

        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == a[i + 1]) {
                currentOccurences++;
            } else {

                if (currentOccurences % 2 != 0) {
                    return a[i];
                }
                currentOccurences = 1;
            }
        }

        return a[a.length - 1];
    } 
}