import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import syntacticalAnalyzer.Parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParseTest {
    public String lexString(String s) throws ParseException {
        InputStream stream = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(stream);
        StringBuilder res = new StringBuilder();
        Token token;
        do {
            lexicalAnalyzer.nextToken();
            token = lexicalAnalyzer.curToken();
            res.append(token.toString()).append(" ");
        } while (token != Token.END);
        return res.toString();
    }

    private void parseString(String s) throws ParseException {
        InputStream stream = new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8));
        new Parser().parse(stream);
    }

    @Test
    public void correctTests() {
        try {
            String s = "lambda n: n + 8";
            Assertions.assertEquals(lexString(s), "LAMBDA VARIABLE COLON VARIABLE PLUS NUM END ");
        } catch (ParseException e) {
            Assertions.fail();
        }
        try {
            String s = "lambda x, y, long_name : long_name * 5 (x + y)";
            Assertions.assertEquals(lexString(s),
                    "LAMBDA VARIABLE COMMA VARIABLE COMMA VARIABLE COLON VARIABLE MUL NUM LPAREN VARIABLE PLUS VARIABLE RPAREN END ");
        } catch (ParseException e) {
            Assertions.fail();
        }
        try {
            String s = "lambda x: x / x";
            Assertions.assertEquals(lexString(s), "LAMBDA VARIABLE COLON VARIABLE DIV VARIABLE END ");
        } catch (ParseException e) {
            Assertions.fail();
        }
        try {
            String s = "lambda x: 100 * 1488 * (88 - x) / 355 + x";
            Assertions.assertEquals(lexString(s),
                    "LAMBDA VARIABLE COLON NUM MUL NUM MUL LPAREN NUM MINUS VARIABLE RPAREN DIV NUM PLUS VARIABLE END ");
        } catch (ParseException e) {
            Assertions.fail();
        }
        //TODO
        try {
            String s = "lambda x: x.add(5, 3)";
            Assertions.assertEquals(lexString(s),
                    "LAMBDA VARIABLE COLON VARIABLE DOT METHOD LPAREN NUM COMMA NUM RPAREN END ");
        } catch (ParseException e) {
            Assertions.fail();
        }
        try {
            String s = "lambda x: x.cuk(5)";
            Assertions.assertEquals(lexString(s),
                    "LAMBDA VARIABLE COLON VARIABLE DOT METHOD LPAREN NUM RPAREN END ");
        } catch (ParseException e) {
            Assertions.fail();
        }
        try {
            String s = "lambda x, y: x.method(5 + 5 * (247 - 2), 38, y)";
            Assertions.assertEquals(lexString(s),
                    "LAMBDA VARIABLE COMMA VARIABLE COLON VARIABLE DOT METHOD LPAREN NUM PLUS NUM MUL LPAREN NUM MINUS NUM RPAREN COMMA NUM COMMA VARIABLE RPAREN END ");
        } catch (ParseException e) {
            Assertions.fail();
        }
        assertDoesNotThrow(() -> {
            String s = "lambda x, y : x.y(5,y.x())";
            parseString(s);
        });
    }

    @Test
    public void incorrectTests() {
        assertThrows(ParseException.class, () -> {
            String s = "x: 267 + 5";
            parseString(s);
        });
        assertThrows(ParseException.class, () -> {
           String s = "lambda x: 5 + x.(12) * 100";
           parseString(s);
        });
        assertThrows(ParseException.class, () -> {
            String s = "lambda x n: 5 * 3";
            parseString(s);
        });
        assertThrows(ParseException.class, () -> {
            String s = "lambba x : x + 5 + 2";
            parseString(s);
        });
        assertThrows(ParseException.class, () -> {
            String s = "lambda x, y: 55 + (6 - y";
            parseString(s);
        });
        assertThrows(ParseException.class, () -> {
            String s = "lambda x: x *+ 48";
            parseString(s);
        });
        assertThrows(ParseException.class, () -> {
            String s = "lambda x: x.cuk(5,)";    // extra comma
            parseString(s);
        });
        assertThrows(ParseException.class, () -> {
            String s = "lambda x: x.cuk(5,4-)";    // extra comma
            parseString(s);
        });
    }
}