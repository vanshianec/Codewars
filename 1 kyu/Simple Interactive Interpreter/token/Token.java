package token;

public class Token {

    private TokenType tokenType;
    private String value;

    public Token(TokenType type, String value){
        tokenType = type;
        this.value = value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getValue() {
        return value;
    }
}
