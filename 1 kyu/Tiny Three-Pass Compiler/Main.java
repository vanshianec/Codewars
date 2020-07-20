import operations.Ast;
import operations.BinaryOperation;
import operations.UnaryOperation;

import java.util.List;

public class Main{
    public static void main(String[] args){
        String prog = "[ x y z ] ( 2*3*x + 5*y - 3*z ) / (1 + 3 + 2*2)";
        Compiler compiler = new Compiler();

        // {'op':'/','a':{'op':'-','a':{'op':'+','a':{'op':'*','a':{'op':'*','a':{'op':'imm','n':2},'b':{'op':'imm','n':3}},'b':{'op':'arg','n':0}},'b':{'op':'*','a':{'op':'imm','n':5},'b':{'op':'arg','n':1}}},'b':{'op':'*','a':{'op':'imm','n':3},'b':{'op':'arg','n':2}}},'b':{'op':'+','a':{'op':'+','a':{'op':'imm','n':1},'b':{'op':'imm','n':3}},'b':{'op':'*','a':{'op':'imm','n':2},'b':{'op':'imm','n':2}}}}
        Ast t1 = new BinaryOperation("/", new BinaryOperation("-", new BinaryOperation("+", new BinaryOperation("*", new BinaryOperation("*", new UnaryOperation("imm", 2), new UnaryOperation("imm", 3)), new UnaryOperation("arg", 0)), new BinaryOperation("*", new UnaryOperation("imm", 5), new UnaryOperation("arg", 1))), new BinaryOperation("*", new UnaryOperation("imm", 3), new UnaryOperation("arg", 2))), new BinaryOperation("+", new BinaryOperation("+", new UnaryOperation("imm", 1), new UnaryOperation("imm", 3)), new BinaryOperation("*", new UnaryOperation("imm", 2), new UnaryOperation("imm", 2))));
        Ast p1 = compiler.pass1(prog);

        Ast p2 = compiler.pass2(p1);

        List<String> p3 = compiler.pass3(p2);
        int res = Simulator.simulate(p3, 4, 8, 16);
        // res = 2
        System.out.println(res);
    }
}