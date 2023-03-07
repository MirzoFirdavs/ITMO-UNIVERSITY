grammar Grammar;

@header {
import java.util.*;
}

parse returns [StringBuilder res] @init {
    Map<String, Integer> vars = new HashMap<>();
    StringBuilder res = new StringBuilder();
    $res = res;
}
    : str[vars, res] EOF
    ;

str [Map <String, Integer> vars, StringBuilder res]: assign[vars, res] str[vars, res] | ;

assign [Map<String, Integer> vars, StringBuilder res]
    : variable=Variable '=' expr[vars] ';' {
        $vars.put($variable.text, $expr.val);
        $res.append($variable.text + " = " + $expr.val + ";" + '\n');
    }
    ;

expr [Map <String, Integer> vars] returns[int val]
    : term[vars] addAndSub[vars, $term.val] { $val = $addAndSub.val; }
    ;

addAndSub [Map <String, Integer> vars, int acc] returns[int val]
    : '+' term[vars] { $acc += $term.val; } addAndSub[vars, $acc] { $val = $addAndSub.val; }
    | '-' term[vars] { $acc -= $term.val; } addAndSub[vars, $acc] { $val = $addAndSub.val; }
    | { $val = $acc; }
    ;

term [Map <String, Integer> vars] returns[int val]
    : power[vars] mulAndDiv[vars, $power.val] { $val = $mulAndDiv.val; }
    ;

mulAndDiv[Map <String, Integer> vars, int acc] returns[int val]
    : '*' power[vars] { $acc *= $power.val; } mulAndDiv[vars, $acc] { $val = $mulAndDiv.val; }
    | '/' power[vars] { $acc /= $power.val; } mulAndDiv[vars, $acc] { $val = $mulAndDiv.val; }
    | { $val = $acc; }
    ;

power[Map <String, Integer> vars] returns[int val]
    : unary[vars] '**' power[vars] { $val = (int) Math.pow($unary.val, $power.val); }
    | unary[vars] { $val = $unary.val; }
    ;

unary [Map <String, Integer> vars] returns[int val]
    : '-' unary[vars] { $val = -$unary.val; }
    | '(' expr[vars] ')' { $val = $expr.val; }
    | number=Number { $val = Integer.parseInt($number.text); }
    | variable=Variable { $val = $vars.get($variable.text); }
    ;

Number: [0-9]+;
Variable : [a-zA-Z0-9]+;
Whitespace : [ \n\t\r]+ -> skip;
