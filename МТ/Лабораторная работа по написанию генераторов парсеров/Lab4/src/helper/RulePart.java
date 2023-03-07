package helper;

public class RulePart {
    public String type;
    public String text;
    public String arguments;

    public RulePart(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public RulePart(String type, String text, String arguments) {
        this.type = type;
        this.text = text;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return text;
    }
}
