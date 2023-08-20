package gen;

import java.util.ArrayList;

public class Node {
    public String type;
    public String text;
    public ArrayList<Node> children = new ArrayList<>();

    public Node(String type) {
        this.type = type;
    }

    public Node(String type, String text) {
        this.type = type;
        this.text = text;
    }

    public Node child(int n) {
        return children.get(n);
    }

    public double val;
}
