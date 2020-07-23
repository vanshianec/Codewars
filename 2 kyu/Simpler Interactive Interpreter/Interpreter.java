
import operations.Ast;
import operations.BinaryOperation;
import operations.UnaryOperation;

import java.util.HashMap;

public class Interpreter {
    private HashMap<String, Double> assignedIdentifiers;

    public Interpreter() {
        assignedIdentifiers = new HashMap<>();
    }

    public double reduceAst(Ast tree) {
        String operator = tree.getOperator();

        /* Check if the operation is Unary and return the value assigned to it */
        if (operator.equals("number")) {
            return Double.parseDouble(((UnaryOperation) tree).getValue());
        } else if (operator.equals("identifier")) {
            UnaryOperation identifier = ((UnaryOperation) tree);
            String variableName = identifier.getValue();
            if (assignedIdentifiers.containsKey(variableName)) {
                return assignedIdentifiers.get(variableName);
            }

            throw new IllegalArgumentException(String.format("ERROR: Invalid identifier. No variable with name %s was found.", variableName));
        }

        /* If the operation is Binary, calculate it based on the given operator */

        Ast left = ((BinaryOperation) tree).getLeft();
        Ast right = ((BinaryOperation) tree).getRight();

        switch (operator) {
            case "+":
                return reduceAst(left) + reduceAst(right);
            case "-":
                return reduceAst(left) - reduceAst(right);
            case "*":
                return reduceAst(left) * reduceAst(right);
            case "/":
                return reduceAst(left) / reduceAst(right);
            case "%":
                return reduceAst(left) % reduceAst(right);
            case "=":
                double identifierValue = reduceAst(right);
                assignedIdentifiers.put(((UnaryOperation) left).getValue(), identifierValue);
                return identifierValue;
        }

        return 0;
    }

    public Double input(String input){
        Scanner scanner = new Scanner(input);
        Parser parser = new Parser(scanner);

        Ast tree = parser.parse();

        if (tree != null) {
            return reduceAst(tree);
        }

        return null;
    }
}
