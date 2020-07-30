package operations;

import java.util.ArrayList;
import java.util.List;

public class BinaryOperation extends Operator {

    private Ast left;
    private Ast right;

    public BinaryOperation(String operator, Ast left, Ast right) {
        super(operator);
        this.left = left;
        this.right = right;
    }

    public Ast getLeft() {
        return left;
    }

    public Ast getRight() {
        return right;
    }

    @Override
    public List<String> getIdentifiers() {
        List<String> identifiers = new ArrayList<>();
        identifiers.addAll(left.getIdentifiers());
        identifiers.addAll(right.getIdentifiers());
        return identifiers;
    }

}
