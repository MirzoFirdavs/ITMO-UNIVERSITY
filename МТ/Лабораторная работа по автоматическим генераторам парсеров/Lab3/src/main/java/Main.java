import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader bis = new BufferedReader(new FileReader("C:\\Users\\User\\Desktop\\Courses\\МТ\\Лабораторная работа по автоматическим генераторам парсеров\\Lab3\\src\\main\\resources\\input.txt"));
            GrammarLexer grammarLexer = new GrammarLexer(CharStreams.fromReader(bis));
            CommonTokenStream commonTokenStream = new CommonTokenStream(grammarLexer);
            GrammarParser grammarParser = new GrammarParser(commonTokenStream);
            System.out.println(grammarParser.parse().res);
        } catch (IOException exc) {
            System.out.println("Parsing input error");
        }
    }
}
