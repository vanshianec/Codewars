import token.Token;
import token.TokenType;

public class Scanner {

    private String sourceCode;
    private char currentChar;
    private int currentPos;

    public Scanner(String input) {
        initScanner(input);
    }

    public void initScanner(String input) {
        sourceCode = input;
        this.currentPos = 0;
        this.currentChar = input.length() != 0 ? sourceCode.charAt(currentPos) : '\0';
    }

    private void error() {
        throw new IllegalArgumentException(String.format("Invalid character %c", currentChar));
    }

    private void advance() {
        if (currentPos + 1 >= sourceCode.length()) {
            currentChar = '\0';
            return;
        }
        currentChar = sourceCode.charAt(++currentPos);
    }

    private void skipWhitespaces() {
        while (Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private String getNumber() {
        StringBuilder num = new StringBuilder();

        while (Character.isDigit(currentChar)) {
            num.append(currentChar);
            advance();
        }

        if (currentChar == '.') {
            num.append(currentChar);
            advance();
            if (Character.isDigit(currentChar)) {
                while (Character.isDigit(currentChar)) {
                    num.append(currentChar);
                    advance();
                }
            } else {
                error();
            }
        }

        return num.toString();
    }

    private String getIdentifier() {
        StringBuilder identifier = new StringBuilder();

        identifier.append(currentChar);
        advance();

        while (currentChar == '_' || Character.isAlphabetic(currentChar) || Character.isDigit(currentChar)) {
            identifier.append(currentChar);
            advance();
        }

        return identifier.toString();
    }

    private boolean isOperator() {
        return currentChar == '+' || currentChar == '-' || currentChar == '*'
                || currentChar == '/' || currentChar == '%';
    }

    private TokenType getOperatorType(char operator) {
        switch (operator) {
            case '+':
                return TokenType.PLUS;
            case '-':
                return TokenType.MINUS;
            case '*':
                return TokenType.MUL;
            case '/':
                return TokenType.DIV;
            case '%':
                return TokenType.MOD;
        }

        return null;
    }

    public Token getNextToken() {

        while (currentChar != '\0') {
            if (currentChar == ' ') {
                skipWhitespaces();
                continue;
            } else if (Character.isDigit(currentChar)) {
                return new Token(TokenType.NUMBER, getNumber());
            } else if (Character.isAlphabetic(currentChar) || currentChar == '_') {
                String identifier = getIdentifier();
                if (identifier.equals("fn")) {
                    return new Token(TokenType.FUNCTION_KEYWORD, identifier);
                }
                return new Token(TokenType.IDENTIFIER, identifier);
            } else if (isOperator()) {
                char operator = currentChar;
                advance();
                return new Token(getOperatorType(operator), operator + "");
            } else if (currentChar == '(') {
                advance();
                return new Token(TokenType.LEFT_PARENTHESIS, "(");
            } else if (currentChar == ')') {
                advance();
                return new Token(TokenType.RIGHT_PARENTHESIS, ")");
            } else if (currentChar == '=') {
                advance();
                if (currentChar == '>') {
                    advance();
                    return new Token(TokenType.FUNCTION_OPERATOR, "=>");
                }
                return new Token(TokenType.EQUALS, "=");
            }

            error();
        }

        return new Token(TokenType.EOF, "");
    }

}
