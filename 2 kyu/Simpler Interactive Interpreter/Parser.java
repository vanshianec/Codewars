import operations.Ast;
import operations.BinaryOperation;
import operations.UnaryOperation;
import token.Token;
import token.TokenType;

public class Parser {

    private Token currentToken;
    private Scanner scanner;

    public Parser(Scanner scanner) {
        initParser(scanner);
    }

    private void initParser(Scanner scanner) {
        this.scanner = scanner;
        currentToken = scanner.getNextToken();
    }

    private void consume(TokenType type) {
        if (currentToken.getTokenType() == type) {
            currentToken = scanner.getNextToken();
            return;
        }

        throw new IllegalArgumentException(String.format("Unexpected token: %s, Expected %s", type, currentToken.getTokenType()));
    }

    private Ast expression() {
        Ast term = term();

        while (currentToken.getTokenType() == TokenType.PLUS || currentToken.getTokenType() == TokenType.MINUS) {
            Token token = currentToken;
            if (token.getTokenType() == TokenType.PLUS) {
                consume(TokenType.PLUS);
            } else if (token.getTokenType() == TokenType.MINUS) {
                consume(TokenType.MINUS);
            }

            term = new BinaryOperation(token.getValue(), term, term());
        }

        return term;
    }

    private Ast term() {
        Ast factor = factor();

        while (currentToken.getTokenType() == TokenType.MUL || currentToken.getTokenType() == TokenType.DIV || currentToken.getTokenType() == TokenType.MOD) {
            Token token = currentToken;
            TokenType type = token.getTokenType();
            if (type == TokenType.MUL) {
                consume(TokenType.MUL);
            } else if (type == TokenType.DIV) {
                consume(TokenType.DIV);
            } else if (type == TokenType.MOD) {
                consume(TokenType.MOD);
            }

            factor = new BinaryOperation(token.getValue(), factor, factor());
        }

        return factor;
    }

    private Ast factor() {
        Token token = currentToken;

        if (token.getTokenType() == TokenType.NUMBER) {
            consume(TokenType.NUMBER);
            return new UnaryOperation("number", token.getValue());
        } else if (token.getTokenType() == TokenType.IDENTIFIER) {
            return assignment();
        } else if (token.getTokenType() == TokenType.LEFT_PARENTHESIS) {
            consume(TokenType.LEFT_PARENTHESIS);
            Ast exp = expression();
            consume(TokenType.RIGHT_PARENTHESIS);
            return exp;
        }

        throw new IllegalArgumentException(String.format("Unexpected token: %s! Expected: %s, %s or %s",
                token.getTokenType(), TokenType.NUMBER, TokenType.IDENTIFIER, TokenType.LEFT_PARENTHESIS));
    }

    private Ast assignment() {
        Token token = currentToken;

        if (token.getTokenType() == TokenType.IDENTIFIER) {
            String identifierValue = currentToken.getValue();
            consume(TokenType.IDENTIFIER);
            if (currentToken.getTokenType() == TokenType.EQUALS) {
                consume(TokenType.EQUALS);
                return new BinaryOperation("=", new UnaryOperation("identifier", identifierValue), expression());
            }

            return new UnaryOperation("identifier", identifierValue);
        }

        throw new IllegalArgumentException(String.format("Unexpected token: %s! Expected: %s", token.getTokenType(), TokenType.IDENTIFIER));
    }

    Ast parse() {
        if (currentToken.getTokenType() == TokenType.EOF) {
            return null;
        }

        Ast ast = expression();
        if (currentToken.getTokenType() != TokenType.EOF) {
            throw new IllegalArgumentException("Invalid source code format!");
        }

        return ast;
    }

}
