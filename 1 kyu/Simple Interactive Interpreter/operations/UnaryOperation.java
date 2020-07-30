package operations;

import java.util.ArrayList;
import java.util.List;

public class UnaryOperation extends Operator {

    private String value;

    public UnaryOperation(String operator, String value) {
        super(operator);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public List<String> getIdentifiers() {
        List<String> identifiers = new ArrayList<>();
        if (super.getOperator().equals("identifier")) {
            identifiers.add(value);
        }

        return identifiers;
    }

}
