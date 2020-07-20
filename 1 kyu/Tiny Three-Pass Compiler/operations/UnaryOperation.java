package operations;

public class UnaryOperation extends Operator {

    private int n;

    public UnaryOperation(String operator, int n){
        super(operator);
        this.n = n;
    }

    public int n(){
        return n;
    }

}
