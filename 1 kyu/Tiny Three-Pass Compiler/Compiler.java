import operations.Ast;

import java.util.List;

public class Compiler {
    public List<String> compile(String input) {
        return pass3(pass2(pass1(input)));
    }

    /**
     * Returns an un-optimized AST
     */
    public Ast pass1(String input) {
        Parser parser = new Parser(input);
        return parser.parse();
    }

    /**
     * Returns an AST with constant expressions reduced
     */
    public Ast pass2(Ast ast) {
        Interpreter interpreter = new Interpreter();
        return interpreter.interpret(ast);
    }

    /**
     * Returns assembly instructions
     */
    public List<String> pass3(Ast ast) {
        AssemblyInstructor instructor = new AssemblyInstructor();
        return instructor.generateInstructions(ast);
    }

}