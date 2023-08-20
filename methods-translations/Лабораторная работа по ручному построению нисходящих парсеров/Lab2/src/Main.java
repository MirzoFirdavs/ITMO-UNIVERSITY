import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.Token;
import syntacticalAnalyzer.Parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import static visualization.GraphicVisualizer.createDotGraph;

public class Main {
    public static void main(String[] args) throws ParseException {
        Parser parser = new Parser();
        String expr = "lambda x, y: x.method(5 + 5 * (247 - 2), 38, y)";
        System.out.println(lexString(expr));
        createDotGraph(expr, parser, "dotsource");
    }

    public static String lexString(String s) throws ParseException {
        InputStream stream = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
        LexicalAnalyzer lexer = new LexicalAnalyzer(stream);
        StringBuilder res = new StringBuilder();
        Token token;
        do {
            lexer.nextToken();
            token = lexer.curToken();
            res.append(token.toString()).append(" ");
        } while (token != Token.END);
        return res.toString();
    }
}