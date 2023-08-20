import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GrammarLexer lexer = new GrammarLexer(CharStreams.fromFileName("src/grammar/calcGrammar.txt"));
        //GrammarLexer lexer = new GrammarLexer(CharStreams.fromFileName("src/grammar/lambdaGrammar.txt"));

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokens);
        ParseTree tree = parser.s();
        Visitor visit = new Visitor();
        visit.visit(tree);
    }
}
