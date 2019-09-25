//Instructions
//Given a mathematical expression as a string you must return the result as a number.
//
//Numbers
//Number may be both whole numbers and/or decimal numbers. The same goes for the returned result.
//
//Operators
//You need to support the following mathematical operators:
//
//Multiplication *
//Division / (as true division)
//Addition +
//Subtraction -
//Operators are always evaluated from left-to-right, and * and / must be evaluated before + and -.
//
//Parentheses
//You need to support multiple levels of nested parentheses, ex. (2 / (2 + 3.33) * 4) - -6
//
//Whitespace
//There may or may not be whitespace between numbers and operators.
//
//An addition to this rule is that the minus sign (-) used for negating numbers and parentheses will never be separated by whitespace. I.e., all of the following are valid expressions.
//
//1-1    // 0
//1 -1   // 0
//1- 1   // 0
//1 - 1  // 0
//1- -1  // 2
//1 - -1 // 2
//
//6 + -(4)   // 2
//6 + -( -4) // 10
//And the following are invalid expressions
//
//1 - - 1    // Invalid
//1- - 1     // Invalid
//6 + - (4)  // Invalid
//6 + -(- 4) // Invalid
//Validation
//You do not need to worry about validation - you will only receive valid mathematical expressions following the above rules.

public class MathEvaluator {


    ////Good Example :
    //   (123.45*(678.90 / (-2.5+ 11.5)-(((80 -(19))) *33.25)) / 20) - (123.45*(678.90 / (-2.5+ 11.5)-(((80 -(19))) *33.25)) / 20) + (13 - 2)/ -(-11)
    public static double calculate(String expression) {
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
            //flip double '-' to '+'
            if (expressionInBrackets.startsWith("-") && openBracketIndex > 0) {
                if (sb.charAt(openBracketIndex - 1) == '-') {
                    //if we have two '-' operators between two numbers we replace them with one '+' operator
                    if (openBracketIndex - 1 > 0 && !isOperator(sb.charAt(openBracketIndex - 2))) {
                        expressionInBrackets = "+" + expressionInBrackets.substring(1);
                    }
                    //else we already have an operator before the two '-' operator so we remove them
                    else {
                        expressionInBrackets = expressionInBrackets.substring(1);
                    }
                    openBracketIndex--;
                }
            }
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
        return c == '/' || c == '*' || c == '-' || c == '+' || c == '(' || c == ')';
    }


}
