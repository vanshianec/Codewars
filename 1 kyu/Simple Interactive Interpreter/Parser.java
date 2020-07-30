import operations.*;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    private Token currentToken;
    private Scanner scanner;
    private List<Function> declaredFunctions;

    public Parser(Scanner scanner) {
        initParser(scanner);
        declaredFunctions = new ArrayList<>();
    }

    public void initParser(Scanner scanner) {
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
            String identifierName = token.getValue();
            if (declaredFunctions.stream().anyMatch(f -> f.getName().equals(identifierName))) {
                return functionCall();
            }
            return assignment();
        } else if (token.getTokenType() == TokenType.FUNCTION_KEYWORD) {
            consume(TokenType.FUNCTION_KEYWORD);
            return functionDeclaration();
        } else if (token.getTokenType() == TokenType.LEFT_PARENTHESIS) {
            consume(TokenType.LEFT_PARENTHESIS);
            if(currentToken.getTokenType() == TokenType.FUNCTION_KEYWORD){
                throw new IllegalArgumentException("Functions can't be wrapped by parenthesis!");
            }
            Ast exp = expression();
            consume(TokenType.RIGHT_PARENTHESIS);
            return exp;
        }

        throw new IllegalArgumentException(String.format("Unexpected factor token: %s!",
                token.getTokenType()));
    }

    private Ast assignment() {

        String identifierValue = currentToken.getValue();
        consume(TokenType.IDENTIFIER);
        if (currentToken.getTokenType() == TokenType.EQUALS) {
            consume(TokenType.EQUALS);
            return new BinaryOperation("=", new UnaryOperation("identifier", identifierValue), expression());
        }

        return new UnaryOperation("identifier", identifierValue);
    }

    private Ast functionCall() {
        Token token = currentToken;
        Function function = declaredFunctions.stream().filter(f -> f.getName().equals(token.getValue())).collect(Collectors.toList()).get(0);
        /* NOTE! this is not a deep copy, only the function parameters have different memory allocation */
        Function copy = new Function(function);

        consume(TokenType.IDENTIFIER);
        List<Ast> functionParams = new ArrayList<>();

        for (int i = 0; i < copy.getIdentifiers().size(); i++) {
            Ast param = expression();
            functionParams.add(param);
        }

        copy.setFuncParams(functionParams);
        return copy;
    }

    private Ast functionDeclaration() {

        Token token = currentToken;

        if (token.getTokenType() == TokenType.IDENTIFIER) {
            String functionName = token.getValue();
            IdentifierStorage.addFunctionName(functionName);
            consume(TokenType.IDENTIFIER);
            List<String> variables = new ArrayList<>();
            while (currentToken.getTokenType() == TokenType.IDENTIFIER) {
                variables.add(currentToken.getValue());
                consume(TokenType.IDENTIFIER);
            }
            consume(TokenType.FUNCTION_OPERATOR);
            /* ensures that function declarations can be overwritten */
            declaredFunctions = declaredFunctions.stream().filter(f -> !f.getName().equals(functionName)).collect(Collectors.toList());
            declaredFunctions.add(new Function("fn", functionName, variables, expression()));
            return null;
        }

        throw new IllegalArgumentException(String.format("Unexpected function token: %s, Expected %s", token.getTokenType(), TokenType.IDENTIFIER));
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
