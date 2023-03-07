package lexicalAnalyzer;

public enum Token {
    NUM, VARIABLE, END,     // базовые
    COLON, COMMA, LAMBDA,   // особые символы задачи
    LPAREN, RPAREN,         // скобки
    PLUS, MINUS, MUL, DIV, // операции
    DOT, METHOD             // методы
}

