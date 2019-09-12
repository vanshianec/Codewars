//My friend John and I are members of the "Fat to Fit Club (FFC)". John is worried because each month a list with the weights of members is published and each month he is the last on the list which means he is the heaviest.
//
//I am the one who establishes the list so I told him: "Don't worry any more, I will modify the order of the list". It was decided to attribute a "weight" to numbers. The weight of a number will be from now on the sum of its digits.
//
//For example 99 will have "weight" 18, 100 will have "weight" 1 so in the list 100 will come before 99. Given a string with the weights of FFC members in normal order can you give this string ordered by "weights" of these numbers?
//
//Example:
//"56 65 74 100 99 68 86 180 90" ordered by numbers weights becomes: "100 180 90 56 65 74 68 86 99"
//
//When two numbers have the same "weight", let us class them as if they were strings and not numbers: 100 is before 180 because its "weight" (1) is less than the one of 180 (9) and 180 is before 90 since, having the same "weight" (9), it comes before as a string.
//
//All numbers in the list are positive numbers and the list can be empty.
//
//Notes
//it may happen that the input string have leading, trailing whitespaces and more than a unique whitespace between two consecutive numbers
//Don't modify the input
//For C: The result is freed.

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class WeightSort {

    public static String orderWeight(String strng) {
        if (strng.trim().isEmpty()) {
            return strng;
        }
        StringBuilder stringBuilder = new StringBuilder(strng);
        Queue<String> numbersIndicesRange = new ArrayDeque<>();
        addNumbersIndexRanges(stringBuilder, numbersIndicesRange);
        String[] numbers = sortNumbersByDigitsSum(strng);
        replaceOldNumbersWithSortedOnes(stringBuilder, numbersIndicesRange, numbers);
        return stringBuilder.toString();
    }

    private static void replaceOldNumbersWithSortedOnes(StringBuilder stringBuilder, Queue<String> numbersIndicesRange, String[] numbers) {
        int regulator = 0;
        for (String number : numbers) {
            String numIndexRange[] = numbersIndicesRange.poll().split(" ");
            int startIndex = Integer.parseInt(numIndexRange[0]);
            int endIndex = Integer.parseInt(numIndexRange[1]);
            stringBuilder.replace(startIndex + regulator, endIndex + regulator, number);
            int currentNumberLength = number.length();
            int oldNumberLength = endIndex - startIndex;
            regulator += currentNumberLength - oldNumberLength;
        }
    }

    private static String[] sortNumbersByDigitsSum(String strng) {
        return Arrays.stream(strng.trim().split("\\s+"))
                .sorted((a, b) -> {
                    int cmp = Integer.compare(getDigitsSum(a), getDigitsSum(b));
                    if (cmp == 0) {
                        return a.compareTo(b);
                    }
                    return cmp;
                })
                .toArray(String[]::new);
    }

    private static void addNumbersIndexRanges(StringBuilder stringBuilder, Queue<String> numbersIndicesRange) {
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) != ' ') {
                int endIndex = i + 1;
                while (endIndex != stringBuilder.length() && stringBuilder.charAt(endIndex) != ' ') {
                    endIndex++;
                }
                numbersIndicesRange.add(i + " " + endIndex);
                i += endIndex - i;
            }
        }
    }

    private static int getDigitsSum(String num) {
        int sum = 0;
        for (int i = 0; i < num.length(); i++) {
            sum += Integer.parseInt(String.valueOf(num.charAt(i)));
        }
        return sum;
    }
}