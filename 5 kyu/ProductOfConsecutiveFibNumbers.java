//The Fibonacci numbers are the numbers in the following integer sequence (Fn):
//
//0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, ...
//
//such as
//
//F(n) = F(n-1) + F(n-2) with F(0) = 0 and F(1) = 1.
//
//Given a number, say prod (for product), we search two Fibonacci numbers F(n) and F(n+1) verifying
//
//F(n) * F(n+1) = prod.
//
//Your function productFib takes an integer (prod) and returns an array:
//
//[F(n), F(n+1), true] or {F(n), F(n+1), 1} or (F(n), F(n+1), True)
//depending on the language if F(n) * F(n+1) = prod.
//
//If you don't find two consecutive F(m) verifying F(m) * F(m+1) = prodyou will return
//
//[F(m), F(m+1), false] or {F(n), F(n+1), 0} or (F(n), F(n+1), False)
//F(m) being the smallest one such as F(m) * F(m+1) > prod.
//
//Examples
//productFib(714) # should return {21, 34, 1},
//                # since F(8) = 21, F(9) = 34 and 714 = 21 * 34
//
//productFib(800) # should return {34, 55, 0},
//                # since F(8) = 21, F(9) = 34, F(10) = 55 and 21 * 34 < 800 < 34 * 55


public class ProductOfConsecutiveFibNumbers {

    public static long[] productFib(long prod) {
        //longest possible fibonacci sequence where the last two members of the sequence...
        //...won't exceed the max value of long when multiplied
        int maxLength = 55;
        long[] fib = new long[maxLength];
        fib[0] = 0;
        fib[1] = 1;
        for (int i = 2; i < fib.length; i++) {
            fib[i] = fib[i - 1] + fib[i - 2];
        }


        for (int i = 0; i < fib.length - 1; i++) {
            if (prod == fib[i] * fib[i + 1]) {
                return new long[]{fib[i], fib[i + 1], 1};
            }
        }

        for (int i = 1; i < fib.length - 1; i++) {
            if (fib[i - 1] * fib[i] < prod && prod < fib[i] * fib[i + 1]) {
                return new long[]{fib[i], fib[i + 1], 0};
            }
        }

        return null;

    }
}

