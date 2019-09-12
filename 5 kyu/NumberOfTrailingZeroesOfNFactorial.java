//Write a program that will calculate the number of trailing zeros in a factorial of a given number.
//
//N! = 1 * 2 * 3 * ... * N
//
//Be careful 1000! has 2568 digits...

public class NumberOfTrailingZeroesOfNFactorial {
        public static int zeros(int n) {
            int trailingZeroes = 0;
            int i = 5;
            while (n / i >= 1) {
                trailingZeroes += n / i;
                i *= 5;
            }
            return trailingZeroes;
        }
}