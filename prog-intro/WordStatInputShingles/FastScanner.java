import java.nio.charset.StandardCharsets;
import java.io.*;
 
public class FastScanner implements AutoCloseable {
    private char currentSymbol;
    private final BufferedReader input;
    private static final byte EOF = -1;
    private static final char NEW_LINE = '\n';
    private static final char NEW_LINE_1 = '\r';
    private static final char APOSTROPHE = '\'';
 
    public FastScanner(InputStream fileName) throws IOException {
        input = new BufferedReader(new InputStreamReader(fileName, "utf8"));
    }

    public FastScanner(File fileName) throws FileNotFoundException, UnsupportedEncodingException {
        input = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf8"));
    }
 
    private void readNextChar() throws IOException {
        currentSymbol = (char)input.read();
    }
 
    public boolean hasNextLine() throws IOException {
        readNextChar();
        return (byte)currentSymbol != EOF;
    }
 
    private boolean inCurrentLine() throws IOException {
        if (currentSymbol == NEW_LINE_1) {
            readNextChar();
            return false;
        }
        return currentSymbol != NEW_LINE_1 && currentSymbol != NEW_LINE && (byte)currentSymbol != EOF;
    }
 
    public String nextWord() throws IOException {
        StringBuilder currentWord = new StringBuilder();
        while (!isCorrectSymbol(currentSymbol)) {
            if (!inCurrentLine()) {
                return "";
            }
            readNextChar();
        }
        while (isCorrectSymbol(currentSymbol)) {
            currentWord.append(currentSymbol);
            readNextChar();
        }
        return currentWord.toString().toLowerCase();
    }
 
    private boolean isCorrectSymbol(char symbol) {
        return Character.isLetter(symbol)
                || Character.getType(symbol) == Character.DASH_PUNCTUATION
                || symbol == APOSTROPHE;
    }
 
    public void close() throws IOException {
        input.close();
    }
}