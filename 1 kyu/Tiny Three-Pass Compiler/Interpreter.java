import operations.Ast;
import operations.BinaryOperation;
import operations.UnaryOperation;

public class Interpreter {

    private Ast reduceAST(Ast tree) {

        if (isUnary(tree.operator())) {
            return tree;
        }

        Ast left = ((BinaryOperation) tree).a();
        Ast right = ((BinaryOperation) tree).b();
        String operator = tree.operator();

        if (!isUnary(left.operator())) {
            left = reduceAST(left);
        }

        if (!isUnary(right.operator())) {
            right = reduceAST(right);
        }

        /*  Both left and right Ast trees are unary operations here,   *
         *  so we check if both of them are numbers and calculate them */
        if(left.operator().equals("imm") && right.operator().equals("imm")){
            switch (operator) {
                case "+":
                    return new UnaryOperation("imm", ((UnaryOperation) left).n() + ((UnaryOperation) right).n());
                case "-":
                    return new UnaryOperation("imm", ((UnaryOperation) left).n() - ((UnaryOperation) right).n());
                case "*":
                    return new UnaryOperation("imm", ((UnaryOperation) left).n() * ((UnaryOperation) right).n());
                case "/":
                    return new UnaryOperation("imm", ((UnaryOperation) left).n() / ((UnaryOperation) right).n());
            }
        }

        /* else if the left or right Ast tree is a variable, calculations can't be made */
        return new BinaryOperation(operator, left, right);
    }

    private boolean isUnary(String operator) {
        return operator.equals("imm") || operator.equals("arg");
    }

    public Ast interpret(Ast tree) {
        return reduceAST(tree);
    }

}
