grammar Grammar;

s : nonTerminal+ terminal+ ;

nonTerminal : NON_TERMINAL_NAME arguments? synt? ':' epsilonOrRule ('|' epsilonOrRule)* ;
arguments : ARGUMENT ;
synt : 'returns' ARGUMENT ;
epsilonOrRule : 'EPS'                                               #eps
              | nonTerminalRule+                                    #notEps
              ;
nonTerminalRule : CODE                                              #ruleCode
                | NON_TERMINAL_NAME ARGUMENT?                       #ruleNonTerminal
                | TERMINAL_NAME                                     #ruleTerminal
                    ;
terminal : TERMINAL_NAME ':' REGEX ;

ARGUMENT : '[' .*? ']' ;
NON_TERMINAL_NAME : [a-z]('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
TERMINAL_NAME : [A-Z]('a'..'z'|'A'..'Z'|'0'..'9'|'_')* ;
CODE : '{' .*? '}' ;
REGEX : '(' .*? ')' ;
WS : [ \t\r\n]+ -> skip ;