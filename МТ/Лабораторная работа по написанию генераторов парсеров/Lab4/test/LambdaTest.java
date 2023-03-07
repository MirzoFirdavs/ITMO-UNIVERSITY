import gen.Lexer;
import gen.Parser;
import org.antlr.v4.runtime.misc.Pair;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.ArrayList;

public class LambdaTest {
    public static void main(String[] args) {
        ArrayList<Pair<String, Boolean>> tests = new ArrayList<>();
        tests.add(new Pair<>("lambda x: x / x", true));
        tests.add(new Pair<>("lambda x, y, long_name: long_name + 5 * (x + y)", true));
        tests.add(new Pair<>("lambda n: n + 8", true));
        tests.add(new Pair<>("lambda x: 100 * 1488 * (88 - x) / 355 + x", true));
        tests.add(new Pair<>("lambda x, y: 55 + (6 - y", false)); // missing ")"
        tests.add(new Pair<>("x: 267 + 5", false)); // missing "lambda"
        tests.add(new Pair<>("lambba x : x + 5 + 2", false)); // incorrect word "lambda"
        tests.add(new Pair<>("lambda x: x *+ 48", false)); // two operations at the same time

        boolean isAllTestsPassed = true;

        for (Pair<String, Boolean> test : tests) {
            try {
                Parser parser = new Parser(new Lexer(new ByteArrayInputStream(
                        test.a.replace('(', '[').replace(')', ']').getBytes())));
                parser.parse();

                if (!test.b) {
                    isAllTestsPassed = false;
                    System.err.println("test failed: " + test.a);
                } else System.out.println("test passed: " + test.a);
            } catch (ParseException e) {
                if (test.b) {
                    isAllTestsPassed = false;
                    System.err.println("test failed: " + test.a);
                    e.printStackTrace();
                } else System.out.println("false test passed: " + test.a);
            }
        }

        if (isAllTestsPassed) System.out.println("\n" + "All tests passed!");
    }
}