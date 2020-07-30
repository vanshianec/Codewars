
public class Main {
    public static void main(String[] args) {

        Interpreter interpreter = new Interpreter();

        interpreter.input("fn echo x => x");
        interpreter.input("fn add x y => x + y");
        Double addRes = interpreter.input("add echo 4 echo 3");
        interpreter.input("fn avg x y => (x + y) / 2");
        Double a = interpreter.input("a = 2");
        Double b = interpreter.input("b = 4");
        Double avgAB = interpreter.input("avg a b");
        interpreter.input("fn inc x => x + 1");
        Double c =interpreter.input("c = 0");
        Double cInc = interpreter.input("c = inc c");
        interpreter.input("fn inc x => x + 2");
        Double cNewInc = interpreter.input("c = inc c");

        System.out.println(addRes);
        System.out.println(a);
        System.out.println(b);
        System.out.println(avgAB);
        System.out.println(c);
        System.out.println(cInc);
        System.out.println(cNewInc);
    }
}
