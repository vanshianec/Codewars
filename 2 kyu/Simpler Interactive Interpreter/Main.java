
public class Main {
    public static void main(String[] args) {

        Interpreter interpreter = new Interpreter();
        Double x = interpreter.input("x = 4 / (3 * 2)");
        Double x1 = interpreter.input("x");
        try{
            Double y = interpreter.input("y");
        }
        catch(IllegalArgumentException ignored){
        }

        Double y = interpreter.input("y = 4 + 2 + 4 * 3 * 2 / 2 - (3 * 3) / (((8 + 3)))");
        Double y1 = interpreter.input("y");
        Double nulll = interpreter.input("           ");

        System.out.println(x);
        System.out.println(x1);
        System.out.println(y);
        System.out.println(y1);
        System.out.println(nulll);
    }
}
