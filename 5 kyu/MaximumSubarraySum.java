//The maximum sum subarray problem consists in finding the maximum sum of a contiguous subsequence in an array or list of integers:
//
//Max.sequence(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
//// should be 6: {4, -1, 2, 1}
//Easy case is when the list is made up of only positive numbers and the maximum sum is the sum of the whole array. If the list is made up of only negative numbers, return 0 instead.
//
//Empty list is considered to have zero greatest sum. Note that the empty list or array is also a valid sublist/subarray.


import java.util.Arrays;

public class MaximumSubarraySum {
    public static int sequence(int[] arr) {
        if(arr.length == 0){
            return 0;
        }

        int biggestElement = Arrays.stream(arr).max().getAsInt();
        if(biggestElement < 0){
            return 0;
        }

        int bestSum = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++){
            int currentSum = arr[i];
            if(currentSum > bestSum){
                bestSum = currentSum;
            }
            for (int j = i + 1; j < arr.length; j++){
                currentSum += arr[j];
                if(currentSum > bestSum){
                    bestSum = currentSum;
                }
            }
        }

        return bestSum;



    }
}