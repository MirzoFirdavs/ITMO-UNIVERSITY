package helper;

public class Terminal {
    public String name;
    public String regex;

    public Terminal(String name, String regex) {
        this.name = name;
        this.regex = regex.substring(1, regex.length() - 1);
    }

    @Override
    public String toString() {
        return name + " : " + regex;
    }
}
