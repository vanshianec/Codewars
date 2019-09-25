//Create a simple calculator that given a string of operators (), +, -, *, / and numbers separated by spaces returns the value of that expression
//
//Example:
//
//Calculator.evaluate("2 / 2 + 3 * 4 - 6") // => 7
//Remember about the order of operations! Multiplications and divisions have a higher priority and should be performed left-to-right. Additions and subtractions have a lower priority and should also be performed left-to-right.

public class Calculator {

    public static Double evaluate(String expression) {
        StringBuilder sb = new StringBuilder(String.join("", expression.split(" ")));

        int openBracketIndex = sb.indexOf("(");
        int closeBracketIndex;
        //calculate all expressions in brackets since brackets have the highest priority
        while (openBracketIndex != -1) {
            //search for nested brackets
            int nextOpenBracketIndex = sb.indexOf("(", openBracketIndex + 1);
            closeBracketIndex = sb.indexOf(")");
            //if we find a nested bracket search for the most inner one
            if (nextOpenBracketIndex < closeBracketIndex && nextOpenBracketIndex != -1) {
                openBracketIndex = nextOpenBracketIndex;
                continue;
            }
            //calculate the expression of the most inner bracket
            String expressionInBrackets = sb.substring(openBracketIndex + 1, closeBracketIndex);
            expressionInBrackets = calculateMultiplicationAndDivision(expressionInBrackets);
            expressionInBrackets = calculateSum(expressionInBrackets);
            sb.replace(openBracketIndex, closeBracketIndex + 1, expressionInBrackets);

            openBracketIndex = sb.indexOf("(");
        }

        String result = sb.toString();
        //after brackets calculate multiplications and divisions since they have the second highest priority
        result = calculateMultiplicationAndDivision(result);
        //after that calculate all additions and subtractions left
        return Double.parseDouble(calculateSum(result));
    }

    private static String calculateSum(String expression) {
        StringBuilder sb = new StringBuilder(expression);
        while (true) {
            int addition = sb.indexOf("+");
            //start form index 1 since we can have an expression which starts with negative number ex. -53.3 - 43
            int subtraction = sb.indexOf("-", 1);
            int firstOperation;
            if (addition == -1) {
                firstOperation = subtraction;
            } else if (subtraction == -1) {
                firstOperation = addition;
            } else {
                firstOperation = Math.min(addition, subtraction);
            }

            //no operators left => break
            if (firstOperation == -1) {
                break;
            }

            String firstNum = getPreviousNumber(sb, firstOperation - 1);
            String secondNum = getNextNumber(sb, firstOperation + 1);

            if (sb.charAt(firstOperation) == '+') {
                String additionResult = String.valueOf(Double.parseDouble(firstNum) + Double.parseDouble(secondNum));
                sb.replace(firstOperation - firstNum.length(), firstOperation + secondNum.length() + 1, additionResult);
            } else {
                String subtractionResult = String.valueOf(Double.parseDouble(firstNum) - Double.parseDouble(secondNum));
                sb.replace(firstOperation - firstNum.length(), firstOperation + secondNum.length() + 1, subtractionResult);
            }
        }

        return sb.toString();
    }

    private static String calculateMultiplicationAndDivision(String expression) {
        StringBuilder sb = new StringBuilder(expression);
        while (true) {
            int multiplication = sb.indexOf("*");
            int division = sb.indexOf("/");
            int firstOperation;
            if (multiplication == -1) {
                firstOperation = division;
            } else if (division == -1) {
                firstOperation = multiplication;
            } else {
                firstOperation = Math.min(multiplication, division);
            }

            //no operators left => break
            if (firstOperation == -1) {
                break;
            }

            String firstNum = getPreviousNumber(sb, firstOperation - 1);
            String secondNum = getNextNumber(sb, firstOperation + 1);

            if (sb.charAt(firstOperation) == '*') {
                String multiplicationResult = String.valueOf(Double.parseDouble(firstNum) * Double.parseDouble(secondNum));
                sb.replace(firstOperation - firstNum.length(), firstOperation + secondNum.length() + 1, multiplicationResult);
            } else {
                String divisionResult = String.valueOf(Double.parseDouble(firstNum) / Double.parseDouble(secondNum));
                sb.replace(firstOperation - firstNum.length(), firstOperation + secondNum.length() + 1, divisionResult);
            }
        }
        return sb.toString();
    }

    private static String getPreviousNumber(StringBuilder expression, int lastIndex) {
        StringBuilder prevNumber = new StringBuilder();

        for (int i = lastIndex; i >= 0; i--) {
            //if the current character is an operator and we haven't reached the end of the expression...
            //we check if the previous character is also an operator and if so that means that the current operator...
            //... is '-' and we have a negative number not a subtraction operation so we add it as part of the number
            if (isOperator(expression.charAt(i)) && i != 0) {
                if (isOperator(expression.charAt(i - 1))) {
                    prevNumber.append(expression.charAt(i));
                }
                break;
            }
            prevNumber.append(expression.charAt(i));
        }
        return prevNumber.reverse().toString();
    }


    private static String getNextNumber(StringBuilder expression, int firstIndex) {
        StringBuilder nextNumber = new StringBuilder();
        if (expression.length() == 0) {
            return "";
        }

        //always add the first character since a number should be at least 1 character and also we can have a...
        //...negative number (starting with '-')
        nextNumber.append(expression.charAt(firstIndex));

        for (int i = firstIndex + 1; i < expression.length(); i++) {
            if (isOperator(expression.charAt(i))) {
                break;
            }
            nextNumber.append(expression.charAt(i));
        }
        return nextNumber.toString();
    }

    private static boolean isOperator(char c) {
        return c == '/' || c == '*' || c == '-' || c == '+';
    }


}
