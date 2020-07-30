
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class IdentifierStorage {

    private static HashMap<String, Double> assignedVariables = new HashMap<>();
    private static Set<String> assignedFunctionNames = new HashSet<>();

    static void addVariable(String variableName, Double value) {
        if (functionHasName(variableName)) {
            throw new IllegalArgumentException(String.format("Variable name '%s' is already taken by a function!", variableName));
        }

        assignedVariables.put(variableName, value);
    }

    static void addFunctionName(String functionName) {
        if (variableHasName(functionName)) {
            throw new IllegalArgumentException(String.format("Function name '%s' is already taken by a variable!", functionName));
        }
        assignedFunctionNames.add(functionName);
    }

    static boolean containsVariable(String identifier) {
        return assignedVariables.containsKey(identifier);
    }

    static Double get(String identifier) {
        return assignedVariables.get(identifier);
    }

    private static boolean variableHasName(String functionName) {
        return assignedVariables.containsKey(functionName);
    }

    private static boolean functionHasName(String identifierName) {
        return assignedFunctionNames.contains(identifierName);
    }
}
