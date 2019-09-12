//Welcome. In this kata, you are asked to square every digit of a number.
//
//For example, if we run 9119 through the function, 811181 will come out, because 92 is 81 and 12 is 1.
//
//Note: The function accepts an integer and returns an integer

public class SquareDigit {

  public int squareDigits(int n) {
    String numToString = String.valueOf(n);
    StringBuilder strBuilder = new StringBuilder();
    for(int i = 0; i < numToString.length();i++){
        int numValue = Integer.parseInt(String.valueOf(numToString.charAt(i)));
        strBuilder.append(numValue * numValue);
    }
    return Integer.parseInt(strBuilder.toString());
  }

}