import helper.NonTerminal;
import helper.RulePart;
import helper.Terminal;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class Visitor extends GrammarBaseVisitor {
    private final ArrayList<NonTerminal> nonTerminals = new ArrayList<>();
    private final ArrayList<Terminal> terminals = new ArrayList<>();

    private final Map<String, Set<String>> FIRST = new HashMap<>();
    private final Map<String, Set<String>> FOLLOW = new HashMap<>();
    private final ArrayList<String> allTokens = new ArrayList<>();

    @Override
    public Object visitS(GrammarParser.SContext ctx) {
        visitChildren(ctx);

        nonTerminals.forEach(System.out::println);
        System.out.println();
        terminals.forEach(System.out::println);

        generateLexer();
        allTokens.add("EOF");
        generateNode();
        findFirst();
        findFollow();
        generateParser();

        return null;
    }

    @Override
    public Object visitNonTerminal(GrammarParser.NonTerminalContext ctx) {
        String arguments;
        if (ctx.arguments() == null) {
            arguments = "";
        } else {
            arguments = (String) visit(ctx.arguments());
        }
        String synt;
        if (ctx.synt() == null) {
            synt = "";
        } else {
            synt = (String) visit(ctx.synt());
        }
        ArrayList<ArrayList<RulePart>> rules = new ArrayList<>();
        ctx.epsilonOrRule().forEach(epsilonOrRule -> rules.add((ArrayList<RulePart>) visit(epsilonOrRule)));

        nonTerminals.add(new NonTerminal(
                ctx.NON_TERMINAL_NAME().getText(),
                arguments,
                synt,
                rules));
        return null;
    }

    @Override
    public Object visitArguments(GrammarParser.ArgumentsContext ctx) {
        return ctx.ARGUMENT().getText().substring(1, ctx.ARGUMENT().getText().length() - 1);
    }

    @Override
    public String visitSynt(GrammarParser.SyntContext ctx) {
        return ctx.ARGUMENT().getText().substring(1, ctx.ARGUMENT().getText().length() - 1);
    }

    @Override
    public ArrayList<RulePart> visitEps(GrammarParser.EpsContext ctx) {
        return null;
    }

    @Override
    public ArrayList<RulePart> visitNotEps(GrammarParser.NotEpsContext ctx) {
        ArrayList<RulePart> rule = new ArrayList<>();
        ctx.children.forEach(child -> rule.add((RulePart) visit(child)));
        return rule;
    }

    @Override
    public Object visitRuleCode(GrammarParser.RuleCodeContext ctx) {
        return new RulePart("code", ctx.CODE().getText().substring(1, ctx.CODE().getText().length() - 1));
    }

    @Override
    public Object visitRuleNonTerminal(GrammarParser.RuleNonTerminalContext ctx) {
        if (ctx.ARGUMENT() == null) {
            return new RulePart("nonTerminal", ctx.NON_TERMINAL_NAME().getText(),
                    "");
        } else {
            return new RulePart("nonTerminal", ctx.NON_TERMINAL_NAME().getText(),
                    ctx.ARGUMENT().getText().substring(1, ctx.ARGUMENT().getText().length() - 1));
        }
    }

    @Override
    public Object visitRuleTerminal(GrammarParser.RuleTerminalContext ctx) {
        return new RulePart("terminal", ctx.TERMINAL_NAME().getText());
    }

    @Override
    public Object visitTerminal(GrammarParser.TerminalContext ctx) {
        terminals.add(new Terminal(ctx.TERMINAL_NAME().getText(), ctx.REGEX().getText()));
        return null;
    }

    private void generateLexer() {
        File src = new File("src/" + "template/Lexer.txt");
        File dest = new File("src/gen/Lexer.java");

        try {
            Files.copy(src.toPath(), dest.toPath());
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/gen/Lexer.java", true));
            // tokens.put(name, regex)
            terminals.forEach(terminal -> {
                try {
                    allTokens.add(terminal.name);
                    bw.write("\t\ttokens.add(\"" + terminal.name + "\");\n");
                    bw.write("\t\tpatterns.add(\"" + terminal.regex + "\");\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bw.write("\t}\n}");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateNode() {
        File src = new File("src/template/Node.txt");
        File dest = new File("src/gen/Node.java");

        try {
            Files.copy(src.toPath(), dest.toPath());
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/gen/Node.java", true));
            // type variable;
            Set<String> syntSet = new HashSet<>();
            for (NonTerminal nonTerminal : nonTerminals) {
                if (nonTerminal.synt != null) {
                    syntSet.addAll(Arrays.asList(nonTerminal.synt.split(", ")));
                }
            }
            for (String synt : syntSet) {
                if (synt.length() > 0) {
                    bw.write("\tpublic " + synt + ";");
                }
            }
            bw.write("\n}");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateParser() {
        File src = new File("src/template/Parser.txt");
        File dest = new File("src/gen/Parser.java");

        try {
            Files.copy(src.toPath(), dest.toPath());
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/gen/Parser.java", true));
            for (NonTerminal nonTerminal : nonTerminals) {
                bw.write("\tpublic Node " + nonTerminal.name + "(" + nonTerminal.arguments + ")  throws ParseException {\n" +
                        "\t\tNode res = new Node(\"" + nonTerminal.name + "\");\n" +
                        "\t\tswitch (lexer.curToken) {\n");
                if (nonTerminal.rules.contains(null)) {
                    for (String token : FOLLOW.get(nonTerminal.name)) {
                        bw.write("\t\t\tcase \"" + token + "\":\n");
                    }
                    bw.write("\t\t\t\tbreak;\n");
                }
                for (String token : allTokens) {
                    boolean wasAlreadyHandled = false;
                    for (ArrayList<RulePart> rule : nonTerminal.rules) {
                        if (rule != null) {
                            ArrayList<RulePart> ruleWithoutCode = new ArrayList<>();
                            for (RulePart rulePart : rule)
                                if (!rulePart.type.equals("code"))
                                    ruleWithoutCode.add(rulePart);
                            boolean equals = false;
                            RulePart firstRulePart = ruleWithoutCode.get(0);
                            if (firstRulePart.type.equals("nonTerminal")) {
                                if (FIRST.get(firstRulePart.text).contains(token) || FIRST.get(firstRulePart.text).contains("EPS") && FOLLOW.get(firstRulePart.text).contains(token))
                                    equals = true;
                            } else {
                                if (firstRulePart.text.equals(token))
                                    equals = true;
                            }
                            if (equals) {
                                if (wasAlreadyHandled) {
                                    System.err.println("This is right branching");
                                } else {
                                    wasAlreadyHandled = true;
                                }
                                bw.write("\t\t\tcase \"" + token + "\":\n");
                                for (RulePart rulePart : rule) {
                                    switch (rulePart.type) {
                                        case "code" -> bw.write("\t\t\t\t" + rulePart.text + "\n");
                                        case "nonTerminal" -> bw.write("\t\t\t\tres.children.add(" + rulePart.text + "(" + rulePart.arguments + "));\n");
                                        case "terminal" -> bw.write("\t\t\t\tres.children.add(new Node(\"" + rulePart.text + "\", lexer.curStr));\n" +
                                                "\t\t\t\tif (!lexer.curToken.equals(\"" + rulePart.text + "\")) {\n" +
                                                "\t\t\t\t\tthrow new ParseException(\"unexpected token\", lexer.curPos);\n" +
                                                "\t\t\t\t}\n" +
                                                "\t\t\t\tlexer.nextToken();\n");
                                    }
                                }
                                bw.write("\t\t\t\tbreak;\n");
                            }

                        }
                    }
                }
                bw.write("""
                        \t\t\tdefault:
                        \t\t\t\tthrow new ParseException("no such rule", lexer.curPos);
                        \t\t}
                        \t\treturn res;
                        \t}

                        """);
            }

            bw.write("}");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void findFirst() {
        nonTerminals.forEach(nonTerminal -> FIRST.put(nonTerminal.name, new HashSet<>()));

        boolean changed = true;
        while (changed) {
            changed = false;
            for (NonTerminal nonTerminal : nonTerminals) {
                Set<String> set = FIRST.get(nonTerminal.name);
                int setSize = set.size();
                for (ArrayList<RulePart> rule : nonTerminal.rules) {
                    if (rule == null) {
                        // EPS
                        set.add("EPS");
                    } else {
                        for (RulePart rulePart : rule) {
                            switch (rulePart.type) {
                                case "nonTerminal":
                                    if (rulePart.text.equals(nonTerminal.name)) {
                                        System.err.print("This is left recursion");
                                    }
                                    boolean continueFlag = false;
                                    for (String first : FIRST.get(rulePart.text)) {
                                        if (first.equals("EPS")) {
                                            continueFlag = true;
                                        }
                                        set.add(first);
                                    }
                                    if (continueFlag) {
                                        continue;
                                    }
                                    break;
                                case "terminal":
                                    set.add(rulePart.text);
                                    break;
                                case "code":
                                    continue;
                            }
                            break;
                        }
                    }
                }
                if (set.size() != setSize) {
                    changed = true;
                }
            }
        }
        System.out.println();
        FIRST.forEach((s, strings) -> {
            System.out.println("FIRST of " + s + " :");
            strings.forEach(string ->
                    System.out.println("\t" + string));
        });
    }

    private void findFollow() {
        nonTerminals.forEach(nonTerminal -> FOLLOW.put(nonTerminal.name, new HashSet<>()));
        FOLLOW.get("s").add("EOF");

        boolean changed = true;
        while (changed) {
            changed = false;
            for (NonTerminal x : nonTerminals) {
                for (ArrayList<RulePart> y : x.rules) {
                    Set<String> first_gamma = new HashSet<>();
                    first_gamma.add("EPS");
                    if (y != null) {
                        ArrayList<RulePart> yWithoutCode = new ArrayList<>();
                        for (RulePart rule : y)
                            if (!rule.type.equals("code"))
                                yWithoutCode.add(rule);
                        for (int i = yWithoutCode.size() - 1; i >= 0; i--) {
                            RulePart z = yWithoutCode.get(i);
                            if (z.type.equals("nonTerminal")) {
                                Set<String> set = FOLLOW.get(z.text);
                                int setSize = set.size();
                                if (set.contains("EPS"))
                                    set.addAll(first_gamma);
                                else {
                                    set.addAll(first_gamma);
                                    if (first_gamma.contains("EPS"))
                                        set.remove("EPS");
                                }
                                if (first_gamma.contains("EPS")) {
                                    set.addAll(FOLLOW.get(x.name));
                                }
                                FOLLOW.replace(z.text, set);
                                if (set.size() != setSize) {
                                    changed = true;
                                }
                            }
                            if (z.type.equals("nonTerminal") && FIRST.get(z.text).contains("EPS")) {
                                first_gamma.addAll(FIRST.get(z.text));
                            } else {
                                first_gamma.clear();
                                if (z.type.equals("nonTerminal"))
                                    first_gamma.addAll(FIRST.get(z.text));
                                else first_gamma.add(z.text);
                            }
                        }
                    }
                }
            }
        }

        System.out.println();
        FOLLOW.forEach((s, strings) -> {
            System.out.println("FOLLOW of " + s + " :");
            strings.forEach(string ->
                    System.out.println("\t" + string));
        });
    }
}
