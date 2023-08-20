package gen;

import java.text.ParseException;

public class Parser {
    public final Lexer lexer;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Node parse() throws ParseException {
        lexer.nextToken();
        return s();
    }

    public Node s()  throws ParseException {
        Node res = new Node("s");
        switch (lexer.curToken) {
            case "MINUS", "NUMBER", "LB" -> {
                res.children.add(expr());
                res.val = res.child(0).val;
            }
            default -> throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node expr()  throws ParseException {
        Node res = new Node("expr");
        switch (lexer.curToken) {
            case "MINUS", "NUMBER", "LB" -> {
                res.children.add(term());
                res.children.add(exprCont(res.child(0).val));
                res.val = res.child(1).val;
            }
            default -> throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node exprCont(double inh)  throws ParseException {
        Node res = new Node("exprCont");
        switch (lexer.curToken) {
            case "PLUS" -> {
                res.children.add(new Node("PLUS", lexer.curStr));
                if (!lexer.curToken.equals("PLUS")) {
                    throw new ParseException("unexpected token", lexer.curPos);
                }
                lexer.nextToken();
                res.children.add(term());
                res.children.add(exprCont(res.child(1).val));
                res.val = inh + res.child(2).val;
            }
            case "MINUS" -> {
                res.children.add(new Node("MINUS", lexer.curStr));
                if (!lexer.curToken.equals("MINUS")) {
                    throw new ParseException("unexpected token", lexer.curPos);
                }
                lexer.nextToken();
                res.children.add(term());
                res.children.add(exprCont(inh - res.child(1).val));
                res.val = res.child(2).val;
            }
            case "RB", "EOF" -> {
                res.children.add(epsExpr());
                res.val = inh;
            }
            default -> throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node epsExpr()  throws ParseException {
        Node res = new Node("epsExpr");
        switch (lexer.curToken) {
            case "RB":
            case "EOF":
                break;
            default:
                throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node term()  throws ParseException {
        Node res = new Node("term");
        switch (lexer.curToken) {
            case "MINUS", "NUMBER", "LB" -> {
                res.children.add(unr());
                res.children.add(termCont(res.child(0).val));
                res.val = res.child(1).val;
            }
            default -> throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node termCont(double inh)  throws ParseException {
        Node res = new Node("termCont");
        switch (lexer.curToken) {
            case "PLUS", "MINUS", "RB", "EOF" -> {
                res.children.add(epsTerm());
                res.val = inh;
            }
            case "MUL" -> {
                res.children.add(new Node("MUL", lexer.curStr));
                if (!lexer.curToken.equals("MUL")) {
                    throw new ParseException("unexpected token", lexer.curPos);
                }
                lexer.nextToken();
                res.children.add(unr());
                res.children.add(termCont(res.child(1).val));
                res.val = inh * res.child(2).val;
            }
            case "DIV" -> {
                res.children.add(new Node("DIV", lexer.curStr));
                if (!lexer.curToken.equals("DIV")) {
                    throw new ParseException("unexpected token", lexer.curPos);
                }
                lexer.nextToken();
                res.children.add(unr());
                res.children.add(termCont(inh / res.child(1).val));
                res.val = res.child(2).val;
            }
            default -> throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node epsTerm()  throws ParseException {
        Node res = new Node("epsTerm");
        switch (lexer.curToken) {
            case "RB":
            case "EOF":
            case "PLUS":
            case "MINUS":
                break;
            default:
                throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node unr()  throws ParseException {
        Node res = new Node("unr");
        switch (lexer.curToken) {
            case "MINUS", "NUMBER", "LB" -> {
                res.children.add(unary());
                res.val = res.child(0).val;
            }
            default -> throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node unary()  throws ParseException {
        Node res = new Node("unary");
        switch (lexer.curToken) {
            case "MINUS" -> {
                res.children.add(new Node("MINUS", lexer.curStr));
                if (!lexer.curToken.equals("MINUS")) {
                    throw new ParseException("unexpected token", lexer.curPos);
                }
                lexer.nextToken();
                res.children.add(lg());
                res.val = -res.child(1).val;
            }
            case "NUMBER", "LB" -> {
                res.children.add(lg());
                res.val = res.child(0).val;
            }
            default -> throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node lg()  throws ParseException {
        Node res = new Node("lg");
        switch (lexer.curToken) {
            case "NUMBER", "LB" -> {
                res.children.add(numOrBr());
                res.children.add(lgCont(res.child(0).val));
                res.val = res.child(1).val;
            }
            default -> throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node lgCont(double inh)  throws ParseException {
        Node res = new Node("lgCont");
        switch (lexer.curToken) {
            case "PLUS", "MINUS", "MUL", "DIV", "RB", "EOF" -> {
                res.children.add(lgExp());
                res.val = inh;
            }
            case "LOG" -> {
                res.children.add(new Node("LOG", lexer.curStr));
                if (!lexer.curToken.equals("LOG")) {
                    throw new ParseException("unexpected token", lexer.curPos);
                }
                lexer.nextToken();
                res.children.add(numOrBr());
                res.children.add(lgCont(res.child(1).val));
                res.val = (int) (Math.log(inh) / Math.log(res.child(2).val));
            }
            default -> throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node lgExp()  throws ParseException {
        Node res = new Node("lgExp");
        switch (lexer.curToken) {
            case "DIV":
            case "RB":
            case "MUL":
            case "EOF":
            case "PLUS":
            case "MINUS":
                break;
            default:
                throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }

    public Node numOrBr()  throws ParseException {
        Node res = new Node("numOrBr");
        switch (lexer.curToken) {
            case "NUMBER" -> {
                res.children.add(new Node("NUMBER", lexer.curStr));
                if (!lexer.curToken.equals("NUMBER")) {
                    throw new ParseException("unexpected token", lexer.curPos);
                }
                lexer.nextToken();
                res.val = Integer.parseInt(res.child(0).text);
            }
            case "LB" -> {
                res.children.add(new Node("LB", lexer.curStr));
                if (!lexer.curToken.equals("LB")) {
                    throw new ParseException("unexpected token", lexer.curPos);
                }
                lexer.nextToken();
                res.children.add(expr());
                res.children.add(new Node("RB", lexer.curStr));
                if (!lexer.curToken.equals("RB")) {
                    throw new ParseException("unexpected token", lexer.curPos);
                }
                lexer.nextToken();
                res.val = res.child(1).val;
            }
            default -> throw new ParseException("no such rule", lexer.curPos);
        }
        return res;
    }
}
