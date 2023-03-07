package helper;

import java.util.ArrayList;

public class NonTerminal {
    public String name;
    public String arguments;
    public String synt;
    public ArrayList<ArrayList<RulePart>> rules;

    public NonTerminal(String name, String arguments, String synt, ArrayList<ArrayList<RulePart>> rules) {
        this.name = name;
        this.arguments = arguments;
        this.synt = synt;
        this.rules = rules;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        rules.forEach(rule -> {
            if (rule == null) {
                sb.append("| EPS ");
            } else {
                sb.append("| ").append(rule).append(" ");
            }
        });
        return name + " " + arguments + " returns " + synt + " : " + sb;
    }
}
