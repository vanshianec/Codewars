package operations;

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
}
