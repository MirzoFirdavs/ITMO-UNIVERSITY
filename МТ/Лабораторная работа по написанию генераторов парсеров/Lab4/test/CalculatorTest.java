import gen.Lexer;
import gen.Node;
import gen.Parser;
import org.antlr.v4.runtime.misc.Pair;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.ArrayList;

public class CalculatorTest {
    public static void main(String[] args) {
        ArrayList<Pair<String, Double>> tests = new ArrayList<>();
        tests.add(new Pair<>("8", (double) 8));
        tests.add(new Pair<>("2 + 3", (double) 5));
        tests.add(new Pair<>("1 + 2 + 3", (double) 6));
        tests.add(new Pair<>("10 / 20", (double) 10 / 20));
        tests.add(new Pair<>("11 * 3 / 5", (double) 33 / 5));
        tests.add(new Pair<>("1 / 3", (double) 1 / 3));
        tests.add(new Pair<>("(2 * 3) + 2", (double) 8));
        tests.add(new Pair<>("(10)", (double) 10));
        tests.add(new Pair<>("1 + (2 + 3)", (double) 6));
        tests.add(new Pair<>("3 * (1 + 2)", (double) 9));
        tests.add(new Pair<>("1 + (15 / 5) * 10 + 1 * (2 - 2 + 1 - 1)", (double) 31));

        boolean isAllTestsPassed = true;

        for (Pair<String, Double> test : tests) {
            try {
                Parser parser = new Parser(new Lexer(new ByteArrayInputStream(
                        test.a.replace('(', '[').replace(')', ']').getBytes())));
                Node ans = parser.parse();

                if (ans.val == test.b) System.out.println("test passed: " + test.a + " = " + test.b);
                else {
                    isAllTestsPassed = false;
                    System.err.println("test failed: " + test.a + " = " + ans.val + ". But should be: " + test.b);
                }
            } catch (ParseException e) {
                isAllTestsPassed = false;
                System.err.println("test failed: " + test.a + " = " + test.b);
            }
        }

        if (isAllTestsPassed) System.out.println("\n" + "All tests passed!");
    }
}