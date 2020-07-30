
import operations.*;

import java.util.*;

public class Interpreter {
    private Scanner scanner;
    private Parser parser;
    private Queue<Queue<Double>> functionsParamsValues;

    public Interpreter() {
        scanner = new Scanner("");
        parser = new Parser(scanner);
        functionsParamsValues = new ArrayDeque<>();
    }

    public double reduceAst(Ast tree) {
        String operator = tree.getOperator();

        /* Check if the operation is Unary and return the value assigned to it */
        if (operator.equals("number")) {
            return Double.parseDouble(((UnaryOperation) tree).getValue());
        } else if (operator.equals("identifier")) {
            UnaryOperation identifier = ((UnaryOperation) tree);
            String variableName = identifier.getValue();

            if (functionsParamsValues.size() > 0) {
                if (functionsParamsValues.peek().size() > 0) {
                    Double value = functionsParamsValues.peek().poll();
                    if (functionsParamsValues.peek() == null || functionsParamsValues.peek().isEmpty()) {
                        functionsParamsValues.poll();
                    }

                    return value;
                }
            } else if (IdentifierStorage.containsVariable(variableName)) {
                return IdentifierStorage.get(variableName);
            }

            throw new IllegalArgumentException(String.format("ERROR: Invalid identifier. No variable with name %s was found.", variableName));
        }
        /* function operator */
        else if (operator.equals("fn")) {
            Queue<Double> reducedParams = new ArrayDeque<>();
            Function function = ((Function) tree);

            for (Ast param : function.getFuncParams()) {
                reducedParams.add(reduceAst(param));
            }

            if (!reducedParams.isEmpty()) {
                functionsParamsValues.add(reducedParams);
            }
            return reduceAst(function.getBlock());
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
                String identifierName = ((UnaryOperation) left).getValue();
                IdentifierStorage.addVariable(identifierName, identifierValue);
                return identifierValue;
        }

        return 0;
    }

    public Double input(String input) {
        scanner.initScanner(input);
        parser.initParser(scanner);

        Ast tree = parser.parse();

        if (tree != null) {
            return reduceAst(tree);
        }

        return null;
    }
}
