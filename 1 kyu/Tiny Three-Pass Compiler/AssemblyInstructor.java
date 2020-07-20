import operations.Ast;
import operations.BinaryOperation;
import operations.UnaryOperation;

import java.util.ArrayList;
import java.util.List;

public class AssemblyInstructor {

    private List<String> instructions;

    public AssemblyInstructor() {
        this.instructions = new ArrayList<>();
    }

    private void createInstructionsWithDFS(Ast tree) {

        String operator = tree.operator();

        if (isUnary(operator)) {
            return;
        }

        Ast left = ((BinaryOperation) tree).a();
        Ast right = ((BinaryOperation) tree).b();

        if (!isUnary(left.operator()) && !isUnary(right.operator())) {
            createInstructionsWithDFS(left);
            createInstructionsWithDFS(right);
            instructions.add("PO");
            instructions.add("SW");
            instructions.add("PO");
            instructions.add(getAssemblyBinaryCommand(operator));
        } else if (!isUnary(left.operator()) && isUnary(right.operator())) {
            createInstructionsWithDFS(left);
            UnaryOperation u = ((UnaryOperation) right);
            instructions.add(getAssemblyUnaryCommand(u.operator(), u.n()));
            instructions.add("SW");
            instructions.add("PO");
            instructions.add(getAssemblyBinaryCommand(operator));
        } else if (isUnary(left.operator()) && !isUnary(right.operator())) {
            createInstructionsWithDFS(right);
            UnaryOperation u = ((UnaryOperation) right);
            instructions.add("PO");
            instructions.add("SW");
            instructions.add(getAssemblyUnaryCommand(u.operator(), u.n()));
            instructions.add(getAssemblyBinaryCommand(operator));
        } else {
            UnaryOperation r = ((UnaryOperation) right);
            UnaryOperation l = ((UnaryOperation) left);
            instructions.add(getAssemblyUnaryCommand(r.operator(), r.n()));
            instructions.add("SW");
            instructions.add(getAssemblyUnaryCommand(l.operator(), l.n()));
            instructions.add(getAssemblyBinaryCommand(operator));
        }

        instructions.add("PU");
    }

    private boolean isUnary(String operator) {
        return operator.equals("imm") || operator.equals("arg");
    }

    private String getAssemblyUnaryCommand(String astOperator, int n) {
        String operator = "";

        switch (astOperator) {
            case "imm":
                operator += "IM ";
                break;
            case "arg":
                operator += "AR ";
                break;
        }

        return operator + n;
    }

    private String getAssemblyBinaryCommand(String astOperator) {
        switch (astOperator) {
            case "+":
                return "AD";
            case "-":
                return "SU";
            case "*":
                return "MU";
            case "/":
                return "DI";
        }

        return "";
    }

    public List<String> generateInstructions(Ast tree) {
        createInstructionsWithDFS(tree);
        return instructions;
    }

}
