package SustainableSorting;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FastScanner {
    private BufferedReader inp;
    private int currentPos = 0, currentSize = 0;
    private static final byte EOF = -1;

    public FastScanner(File input) throws FileNotFoundException, UnsupportedEncodingException {
        inp = new BufferedReader(new InputStreamReader(new FileInputStream(input), StandardCharsets.UTF_8));
    }
    public FastScanner(InputStream input) {
        inp = new BufferedReader(new InputStreamReader(input));
    }

    private int size = 1024;
    private char[] buffer = new char[size];

    private void readBytes() throws IOException {
        do {
            currentSize = inp.read(buffer, 0, size);
        } while (currentSize == 0);
        currentPos = 0;
    }

    public boolean hasNext() throws IOException {
        if (currentSize <= currentPos) {
            readBytes();
        }
        return (currentSize != -1);
    }

    public void close() throws IOException {
        inp.close();
    }

    public boolean hasNextLine() throws IOException {
        if (currentSize <= currentPos) {
            readBytes();
        }
        if (buffer[currentPos] == '\n') {
            currentPos++;
            return true;
        }
        if (buffer[currentPos] == '\r') {
            currentPos++;
            if (currentSize <= currentPos) {
                readBytes();
            }
            if (buffer[currentPos] == '\n') currentPos++;
            return true;
        }
        return false;
    }

    public String nextWord() throws IOException {
        if (currentPos >= currentSize) {
            readBytes();
        }
        StringBuilder readString = new StringBuilder();
        if(!(this.hasNext())) {
            return readString.toString();
        }
        while (currentPos < currentSize && buffer[currentPos] == ' ') {
            currentPos++;
            if (currentPos >= currentSize) {
                readBytes();
            }
        }
        if(!(this.hasNext())) {
            return readString.toString();
        }
        while (currentPos < currentSize && !Character.isWhitespace((char)buffer[currentPos])) {
            char currentSymbol = (char)(buffer[currentPos]);
            currentPos++;
            readString.append(currentSymbol);
            if (currentPos >= currentSize) {
                readBytes();
            }
            if (!(this.hasNext())) {
                return readString.toString();
            }
        }
        if (currentPos < currentSize) {
            currentPos++;
        }
        return readString.toString();
    }
}