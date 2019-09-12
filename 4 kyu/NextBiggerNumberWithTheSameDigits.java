//You have to create a function that takes a positive integer number and returns the next bigger number formed by the same digits:
//
//12 ==> 21
//513 ==> 531
//2017 ==> 2071
//If no bigger number can be composed using those digits, return -1:
//
//9 ==> -1
//111 ==> -1
//531 ==> -1


import java.util.TreeMap;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class NextBiggerNumberWithTheSameDigits {

    public static long nextBiggerNumber(long n) {

        int[] digits = Arrays.stream(String.valueOf(n).split("")).mapToInt(Integer::parseInt).toArray();
        for (int i = digits.length - 2; i >= 0; i--) {
            int index = getNextBigNumIndex(digits, i + 1, digits[i]);
            if (index != -1) {
                int temp = digits[i];
                digits[i] = digits[index];
                digits[index] = temp;
                sortNextNums(digits, i + 1);
                return Long.parseLong(String.join("", Arrays.stream(digits).mapToObj(String::valueOf).toArray(String[]::new)));
            }
        }


        return -1;
    }

    private static int getNextBigNumIndex(int[] digits, int index, int currentNum) {
        TreeMap<Integer, Integer> lowestNumsIndices = new TreeMap<>();
        for (int i = index; i < digits.length; i++) {
            if (digits[i] > currentNum) {
                lowestNumsIndices.putIfAbsent(digits[i], i);
            }
        }
        if (lowestNumsIndices.isEmpty()) {
            return -1;
        }

        return lowestNumsIndices.firstEntry().getValue();
    }

    private static void sortNextNums(int[] digits, int index) {
        List<Integer> sorted = new ArrayList<>();
        for (int i = index; i < digits.length; i++) {
            sorted.add(digits[i]);
        }

        sorted.sort((a, b) -> a - b);

        for (int i = index; i < digits.length; i++) {
            digits[i] = sorted.remove(0);
        }

    }


}
