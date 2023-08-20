package gen;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Lexer {
    public final InputStream is;
    public int curChar;
    public int curPos = 0;
    public String curToken;
    public String curStr;
    public final ArrayList<String> tokens = new ArrayList<>();
    public final ArrayList<String> patterns = new ArrayList<>();

    public Lexer(InputStream is) throws ParseException {
        this.is = is;
        nextChar();
        addTokens();
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new ParseException(e.getMessage(), curPos);
        }
    }

    public void nextToken() throws ParseException {
        while (Character.isWhitespace(curChar)) {
            nextChar();
        }
        if (curChar == -1) {
            curToken = "EOF";
            curStr = "";
            return;
        }
        StringBuilder sb = new StringBuilder();
        String resTokenName = "";
        boolean flag;
        boolean flag1 = false;
        while (true) {
            sb.append((char) curChar);
            flag = false;
            for (int i = 0; i < tokens.size(); i++) {
                String token = tokens.get(i);
                String pattern = patterns.get(i);
                Pattern p = Pattern.compile(pattern);
                if (p.matcher(sb.toString()).matches()) {
                    resTokenName = token;
                    flag = true;
                    flag1 = true;
                    break;
                }
            }
            if (!flag && flag1) {
                sb.deleteCharAt(sb.length() - 1);
                if (sb.length() == 0) {
                    throw new ParseException("unknown token", curPos - 1);
                }
                break;
            } else {
                if (sb.length() > 1000) {
                    throw new ParseException("unknown token", -1);
                }
                nextChar();
            }
        }

        curToken = resTokenName;
        curStr = sb.toString();
    }

    private void addTokens() {
        tokens.add("PLUS");
        patterns.add("\\+");
        tokens.add("MINUS");
        patterns.add("-");
        tokens.add("MUL");
        patterns.add("\\*");
        tokens.add("DIV");
        patterns.add("/");
        tokens.add("NUMBER");
        patterns.add("[0-9]+");
        tokens.add("LB");
        patterns.add("\\[");
        tokens.add("RB");
        patterns.add("\\]");
        //TODO
        tokens.add("LOG");
        patterns.add("\\/\\/");
    }
}
