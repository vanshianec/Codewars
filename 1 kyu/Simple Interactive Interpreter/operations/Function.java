package operations;

import java.util.*;

public class Function extends Operator {

    private String name;
    private List<String> identifiers;
    private List<Ast> funcParams;
    private Ast block;

    public Function(String operator, String name, List<String> identifiers, Ast block) {
        super(operator);
        this.name = name;
        validate(identifiers, block.getIdentifiers());
        this.identifiers = identifiers;
        this.block = block;
    }

    public Function(Function other) {
        super(other.getOperator());
        this.name = other.name;
        this.identifiers = other.identifiers;
        this.funcParams = new ArrayList<>();
        this.block = other.block;
    }

    private void validate(List<String> funcParams, List<String> blockParams) {

        if (funcParams.size() == blockParams.size()) {
            Collections.sort(funcParams);
            Collections.sort(blockParams);
            for (int i = 0; i < funcParams.size(); i++) {
                if (!funcParams.get(i).equals(blockParams.get(i))) {
                    throw new IllegalArgumentException(String.format("Function params and block params names don't match in function : '%s'", name));
                }
            }

            Set<String> uniqueFuncParams = new HashSet<>(funcParams);
            if(uniqueFuncParams.size() < funcParams.size()){
                throw new IllegalArgumentException(String.format("Function params should be unique! In function '%s'", name));
            }

            Set<String> uniqueBlockParams = new HashSet<>(blockParams);
            if(uniqueBlockParams.size() < blockParams.size()){
                throw new IllegalArgumentException(String.format("Function body params should be unique! In function '%s'", name));
            }

            return;
        }

        throw new IllegalArgumentException(String.format("Function params don't match the block params in function : '%s'", name));
    }

    public Ast getBlock() {
        return block;
    }

    public void setFuncParams(List<Ast> funcParams) {
        this.funcParams = funcParams;
    }

    public List<Ast> getFuncParams() {
        return funcParams;
    }

    public String getName() {
        return name;
    }

    @Override
    public List<String> getIdentifiers() {
        return identifiers;
    }
}
