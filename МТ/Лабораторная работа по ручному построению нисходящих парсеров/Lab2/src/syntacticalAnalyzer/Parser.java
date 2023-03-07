package syntacticalAnalyzer;

import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.Token;

import java.io.InputStream;
import java.text.ParseException;

import static lexicalAnalyzer.Token.*;

public class Parser {
    LexicalAnalyzer lexicalAnalyzer;

    public Tree parse(InputStream input) throws ParseException {
        lexicalAnalyzer = new LexicalAnalyzer(input);
        lexicalAnalyzer.nextToken();
        return S();
    }

    private void consume(Token token) throws ParseException {
        if (lexicalAnalyzer.curToken() != token) {
            throw new ParseException("Expected token" + token.toString() + "on the position: ", lexicalAnalyzer.curPos());
        }
        lexicalAnalyzer.nextToken();
    }

    private Tree S() throws ParseException {
        if (lexicalAnalyzer.curToken() == Token.LAMBDA) {
            lexicalAnalyzer.nextToken();
            Tree variables = V();
            consume(Token.COLON);
            Tree expression = E();
            return new Tree("S", new Tree("lambda"), variables, new Tree(":"), expression);
        } else {
            throw new ParseException("Expected token LAMBDA on the position: ", lexicalAnalyzer.curPos());
        }
    }

    private Tree V() throws ParseException {
        switch (lexicalAnalyzer.curToken()) {
            case COLON -> {
                return new Tree("V");
            }
            case VARIABLE -> {
                lexicalAnalyzer.nextToken();
                return new Tree("V", new Tree("letter"), V2());
            }
            default -> throw new ParseException("Unexpected token on the position: ", lexicalAnalyzer.curPos());
        }
    }

    private Tree V2() throws ParseException {
        switch (lexicalAnalyzer.curToken()) {
            case COMMA -> {
                lexicalAnalyzer.nextToken();
                lexicalAnalyzer.nextToken();
                return new Tree("V'", new Tree(","), V2());
            }
            case COLON -> {
                return new Tree("V'");
            }
            default -> throw new ParseException("Unexpected token on the position: ", lexicalAnalyzer.curPos());
        }
    }

    private Tree E() throws ParseException {
        return new Tree("E", F(), A());
    }

    private Tree F() throws ParseException {
        return new Tree("F", N(), M());
    }

    private Tree A() throws ParseException {
        switch (lexicalAnalyzer.curToken()) {
            case PLUS -> {
                lexicalAnalyzer.nextToken();
                return new Tree("A", new Tree("+"), F(), A());
            }
            case MINUS -> {
                lexicalAnalyzer.nextToken();
                return new Tree("A", new Tree("-"), F(), A());
            }
            default -> {
                return new Tree("A");
            }
        }
    }

    private Tree M() throws ParseException {
        switch (lexicalAnalyzer.curToken()) {
            case MUL -> {
                lexicalAnalyzer.nextToken();
                return new Tree("M", new Tree("*"), N(), M());
            }
            case DIV -> {
                lexicalAnalyzer.nextToken();
                return new Tree("M", new Tree("/"), N(), M());
            }
            default -> {
                return new Tree("M");
            }
        }
    }
    private Tree N() throws ParseException {
        switch (lexicalAnalyzer.curToken()) {
            case VARIABLE -> {
                return new Tree("N", H());
            }
            case NUM -> {
                lexicalAnalyzer.nextToken();
                return new Tree("N", new Tree("number"));
            }
            case LPAREN -> {
                lexicalAnalyzer.nextToken();
                Tree expression = E();
                consume(Token.RPAREN);
                return new Tree("N", new Tree("("), expression, new Tree(")"));
            }
            default -> throw new ParseException("Unexpected token on the position: ", lexicalAnalyzer.curPos());
        }
    }
    private Tree H() throws ParseException {
        if (lexicalAnalyzer.curToken() == Token.VARIABLE) {
            lexicalAnalyzer.nextToken();
            return new Tree("H", new Tree("letter"), H2());
        }
        throw new ParseException("Unexpected token on the position: ", lexicalAnalyzer.curPos());
    }

    private Tree H2() throws ParseException {
        switch (lexicalAnalyzer.curToken()) {
            case DOT -> {
                lexicalAnalyzer.nextToken();
                consume(METHOD);
                consume(LPAREN);
                Tree expression = P();
                consume(RPAREN);
                return new Tree("H2", new Tree("."), new Tree("method"), new Tree("("),
                        expression, new Tree(")"));
            }
            case COMMA, PLUS, MINUS, DIV, MUL, RPAREN, END -> {
                return new Tree("H2");
            }
        }
        throw new ParseException("Unexpected token on the position: ", lexicalAnalyzer.curPos());
    }

    private Tree P() throws ParseException {
        switch (lexicalAnalyzer.curToken()) {
            case VARIABLE, NUM, LPAREN -> {
                return new Tree("P", E(), P2());
            }
            case RPAREN -> {
                return new Tree("P");
            }
        }
        throw new ParseException("Unexpected token on the position: ", lexicalAnalyzer.curPos());
    }

    private Tree P2() throws ParseException {
        switch (lexicalAnalyzer.curToken()) {
            case COMMA -> {
                lexicalAnalyzer.nextToken();
                return new Tree("P'", new Tree(","), P3());
            }
            case RPAREN -> {
                return new Tree("P'");
            }
            default -> {
                System.out.println(lexicalAnalyzer.curToken());
                throw new ParseException("Unexpected token on the position: ", lexicalAnalyzer.curPos());
            }
        }
    }

    private Tree P3() throws ParseException {
        switch (lexicalAnalyzer.curToken()) {
            case VARIABLE, NUM, LPAREN -> {
                return new Tree("P''", E(), P2());
            }
            default -> throw new ParseException("Unexpected token on the position: ", lexicalAnalyzer.curPos());
        }
    }
}