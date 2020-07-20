package operations;

public class BinaryOperation extends Operator {

    private Ast a;
    private Ast b;

    public BinaryOperation(String operator, Ast a, Ast b){
        super(operator);
        this.a = a;
        this.b = b;
    }

    public Ast a(){
        return a;
    }

    public Ast b(){
        return b;
    }


}
