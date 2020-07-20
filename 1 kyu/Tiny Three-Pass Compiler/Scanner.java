import token.Token;
import token.TokenType;

public class Scanner {

    /* Input source code of the program */
    private String input;
    private int pos;
    private char currentChar;

    public Scanner(String input){
        this.input = input;
        pos = 0;
        currentChar = input.charAt(0);
    }

    void advance(){
        pos++;
        if(pos >= input.length()){
            currentChar = '\0';
            return;
        }

        currentChar = input.charAt(pos);
    }

    void skipWhiteSpace(){
        while(currentChar == ' '){
            advance();
        }
    }

    void error(char c){
        throw new IllegalArgumentException(String.format("Invalid character: %c!", c));
    }

    String readInteger(){
        StringBuilder num = new StringBuilder();

        while(Character.isDigit(currentChar)){
            num.append(currentChar);
            advance();
        }

        return num.toString();
    }

    String readVariable(){
        StringBuilder variable = new StringBuilder();

        while(Character.isAlphabetic(currentChar)){
            variable.append(currentChar);
            advance();
        }

        return variable.toString();
    }

    Token getNextToken(){

        /* Input example: */
        /* [ x y z ] ( 2*3*x + 5*y - 3*z ) / (1 + 3 + 2*2) */

        while(currentChar != '\0'){
            if(currentChar == ' '){
                skipWhiteSpace();
                continue;
            }
            if(Character.isDigit(currentChar)){
                return new Token(TokenType.INTEGER, readInteger());
            }
            if(Character.isAlphabetic(currentChar)){
                return new Token(TokenType.VARIABLE, readVariable());
            }
            if(currentChar == '+'){
                advance();
                return new Token(TokenType.PLUS, "+");
            }
            if(currentChar == '-'){
                advance();
                return new Token(TokenType.MINUS, "-");
            }
            if(currentChar == '*'){
                advance();
                return new Token(TokenType.MULTIPLICATION, "*");
            }
            if(currentChar == '/'){
                advance();
                return new Token(TokenType.DIVISION, "/");
            }
            if(currentChar == '('){
                advance();
                return new Token(TokenType.LEFT_PARENTHESIS, "(");
            }
            if(currentChar == ')'){
                advance();
                return new Token(TokenType.RIGHT_PARENTHESIS, ")");
            }
            if(currentChar == '['){
                advance();
                return new Token(TokenType.LEFT_BRACKET, "[");
            }
            if(currentChar == ']'){
                advance();
                return new Token(TokenType.RIGHT_BRACKET, "]");
            }

            /* no valid characters found, throw an error */
            error(currentChar);
        }

        return new Token(TokenType.EOF, "");
    }

}
