package operations;

public class UnaryOperation extends Operator {

    private String value;

    public UnaryOperation(String operator, String value) {
        super(operator);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
