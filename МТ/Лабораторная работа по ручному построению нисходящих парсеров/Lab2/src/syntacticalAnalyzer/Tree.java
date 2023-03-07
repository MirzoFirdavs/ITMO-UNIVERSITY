package syntacticalAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tree {
    public String node;
    public List<Tree> children = new ArrayList<>();

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
    }
}