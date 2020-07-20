import operations.Ast;
import operations.BinaryOperation;
import operations.UnaryOperation;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Scanner scanner;
    private Token currentToken;
    private int variablesCount;

    public Parser(String input) {
        scanner = new Scanner(input);
        currentToken = scanner.getNextToken();
        variablesCount = 0;
    }

    void consume(TokenType type) {
        if (currentToken.getType() == type) {
            currentToken = scanner.getNextToken();
            return;
        }

        throw new IllegalArgumentException(String.format("Unexpected token : %s ! Expected to have %s.", type, currentToken.getType()));
    }

    Ast factor() {
        Token token = currentToken;

        if (token.getType() == TokenType.INTEGER) {
            consume(TokenType.INTEGER);
            return new UnaryOperation("imm", Integer.parseInt(token.getValue()));
        } else if (token.getType() == TokenType.VARIABLE) {
            consume(TokenType.VARIABLE);
            return new UnaryOperation("arg", variablesCount++);
        } else if (token.getType() == TokenType.LEFT_PARENTHESIS) {
            consume(TokenType.LEFT_PARENTHESIS);
            Ast expression = expression();
            consume(TokenType.RIGHT_PARENTHESIS);
            return expression;
        }

        throw new IllegalArgumentException(String.format("Unexpected token: %s! Expected to have %s, %s or %s",
                token.getType(), TokenType.INTEGER, TokenType.VARIABLE, TokenType.LEFT_PARENTHESIS));
    }

    Ast term() {
        Ast factor = factor();

        while (currentToken.getType() == TokenType.MULTIPLICATION || currentToken.getType() == TokenType.DIVISION) {
            Token token = currentToken;
            if (token.getType() == TokenType.MULTIPLICATION) {
                consume(TokenType.MULTIPLICATION);
            } else if (token.getType() == TokenType.DIVISION) {
                consume(TokenType.DIVISION);
            }

            factor = new BinaryOperation(token.getValue(), factor, factor());
        }

        return factor;
    }

    Ast expression() {
        Ast term = term();

        while (currentToken.getType() == TokenType.PLUS || currentToken.getType() == TokenType.MINUS) {
            Token token = currentToken;
            if (token.getType() == TokenType.PLUS) {
                consume(TokenType.PLUS);
            } else if (token.getType() == TokenType.MINUS) {
                consume(TokenType.MINUS);
            }

            term = new BinaryOperation(token.getValue(), term, term());
        }

        return term;
    }

    List<String> argumentList() {
        List<String> argList = new ArrayList<>();

        while (currentToken.getType() == TokenType.VARIABLE) {
            argList.add(currentToken.getValue());
            consume(TokenType.VARIABLE);
        }

        return argList;
    }

    Ast function() {
        Token token = currentToken;

        if (token.getType() == TokenType.LEFT_BRACKET) {
            consume(TokenType.LEFT_BRACKET);
            //TODO
            List<String> argList = argumentList();
            consume(TokenType.RIGHT_BRACKET);
            return expression();
        }

        throw new IllegalArgumentException(String.format("Unexpected token : %s ! Expected to have %s.", token.getType(), TokenType.LEFT_PARENTHESIS));
    }

    Ast parse() {
        return function();
    }

}
