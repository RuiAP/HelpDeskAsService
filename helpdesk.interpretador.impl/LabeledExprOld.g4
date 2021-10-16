grammar LabeledExprOld;

prog: stat ;
stat: expr NEWLINE # printExpr
| ID '=' expr NEWLINE # assign
| NEWLINE # blank
;
expr: expr op=('*'|'/') expr # MulDiv
| expr op=('+'|'-') expr# AddSub
| INT # int
| ID # id
| '(' expr ')' # parens
;
NEWLINE : [\r\n]+ ;
INT:[0-9]+;
ID:[a-z]+;
MUL : '*' ; // assigns token name to '*' used above in grammar
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
ATR : '=' ;
LPR : '(' ;
RPR : ')' ;