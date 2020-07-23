package operations;

public abstract class Operator implements Ast{

    private String operator;

    public Operator(String operator){
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }
}
