package operations;

public abstract class Operator implements Ast{

    private String operator;

    protected Operator(String operator){
        this.operator = operator;
    }

    @Override
    public String operator() {
        return operator;
    }
}
