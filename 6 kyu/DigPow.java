//Some numbers have funny properties. For example:
//
//89 --> 8¹ + 9² = 89 * 1
//
//695 --> 6² + 9³ + 5⁴= 1390 = 695 * 2
//
//46288 --> 4³ + 6⁴+ 2⁵ + 8⁶ + 8⁷ = 2360688 = 46288 * 51
//
//Given a positive integer n written as abcd... (a, b, c, d... being digits) and a positive integer p
//
//we want to find a positive integer k, if it exists, such as the sum of the digits of n taken to the successive powers of p is equal to k * n.
//In other words:
//
//Is there an integer k such as : (a ^ p + b ^ (p+1) + c ^(p+2) + d ^ (p+3) + ...) = n * k
//
//If it is the case we will return k, if not return -1.
//
//Note: n and p will always be given as strictly positive integers.


 public class DigPow {

        public static long digPow(int n, int p) {

            long totalSum = 0;
            int nCopy = n;
            int currentPower = String.valueOf(n).length() - 1 + p;
            while (n > 0) {
                int lastDigit = n % 10;
                totalSum += Math.pow(lastDigit, currentPower);
                currentPower--;
                n /= 10;
            }

            double result = (double) totalSum / nCopy;

            if (result <= 0) {
                return -1;
            }

            if (result % 1 == 0) {
                return (long) result;
            }

            return -1;
        }

}