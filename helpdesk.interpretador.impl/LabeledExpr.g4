grammar LabeledExpr;

prog: method;

method:  statement* (returnStatement)?;

identifier: Identifier;

statement
:
	 ifElseStatement
	| whileStatement
	| printStatement
	| variableAssignmentStatement
	| returnStatement
	| allRecordsStatement
	| findRecordStatement
	| findFirstRecordStatement
	| sendEmailStatement
	| getSubStringStatement
	|  '{' statement* '}'
	| sleepStatement
;

variableAssignmentStatement: identifier EQ ((expression SC)|statement)  #variableAssignmentExpression;

getSubStringStatement: 'getSubString' LP expression ',' expression ',' expression RP SC #getSubStringExpression;

printStatement: 'print' LP(expression) RP SC #printExpression;

whileStatement: 'forAll' LP identifier ':' identifier RP statement #whileExpression;

ifElseStatement: 'if' LP expression RP statement ('else' statement)? #ifElseExpression;

returnStatement: 'return' expression SC #returnExpression;

allRecordsStatement: 'allRecords' LP expression RP SC #allRecordsExpression;

findRecordStatement: 'findRecords' LP expression ',' expression ',' expression RP SC #findRecordExpression;

findFirstRecordStatement: 'findFirstRecord' LP expression ',' expression ',' expression RP SC #findFirstRecordExpression;

sendEmailStatement: 'sendEmail' LP expression ',' expression RP SC #sendEmailExpression;

sleepStatement : 'sleep' LP expression RP SC #sleepExpression;

expression
:
      NOT expression # notExpression
	| expression TIMES expression # mulExpression
	| expression DIV expression # divExpression
	| expression PLUS expression # addExpression
	| expression MINUS expression # subExpression
	| expression MOD expression # modExpression
	| expression LT(EQ)? expression # lessThanExpression
	| expression GT(EQ)? expression # greaterthanExpression
	| expression (EQ|NOT) EQ expression # equalityExpression
	| expression AND expression # andExpression
	| expression OR expression # orExpression
	| identifier ISFILLED # filledExpression
	| expression CONCAT expression # concatExpression
	| identifier (TAG|ATTRIBUTE) expression # memoryAccessObjectXMLExpression
	| identifier COLUMN expression #memoryAccessColumnCSVExpression
	| COLUMN expression #columnCSVExpression
	| (TAG|ATTRIBUTE) expression #objectXMLExpression
	| '(' expression ')' # ParenthesesExpression
	| STRING # stringExpression
	| CHAR # characterExpression
	| ('+'|'-')? IntegerLiteral #integerLitExpression
	| ('+'|'-')? identifier #identifierExpression
	| BooleanLiteral # boolLitExpression
;
TAG:'tag';
ATTRIBUTE:'attribute';
COLUMN:'column';
CONCAT:'$';
ISFILLED:'.IsFilled';
DIV:'/';
OR:'||';
GT:'>';
AND:'&&';
MOD:'%';
LT:'<';
PLUS:'+';
MINUS:'-';
TIMES:'*';
NOT:'!';
LSB:'[';
RSB:']';
LP:'(';
RP:')';
RETURN:'return';
EQ:'=';
BooleanLiteral:'true'| 'false';
SC:';';

Identifier:JavaLetter JavaLetterOrDigit*;
WS: [ \r\t\n]+ -> skip ;
STRING:	'"'(ESC_SEQ| ~( '\\' | '"' ))* '"' ;
CHAR: '\''(ESC_SEQ| ~( '\'' | '\\' )) '\'' ;
IntegerLiteral: DecimalIntegerLiteral ;


fragment JavaLetter : [a-zA-Z$_] ;
fragment JavaLetterOrDigit:[a-zA-Z0-9$_] ;
fragment DecimalIntegerLiteral: DecimalNumeral IntegertypeSuffix? ;
fragment IntegertypeSuffix: [lL] ;
fragment DecimalNumeral: '0' | NonZeroDigit (Digits?| Underscores Digits) ;
fragment Digits: Digit (DigitsAndUnderscores? Digit)? ;
fragment Digit:'0'| NonZeroDigit ;
fragment NonZeroDigit:[1-9] ;
fragment DigitsAndUnderscores: DigitOrUnderscore+ ;
fragment DigitOrUnderscore: Digit| '_' ;
fragment Underscores: '.'+ ;
fragment HEX_DIGIT:	(	'0' .. '9'| 'a' .. 'f'| 'A' .. 'F') ;
fragment ESC_SEQ: '\\'('b'| 't'| 'n'| 'f'| 'r'| '"'| '\''| '\\')| UNICODE_ESC| OCTAL_ESC ;
fragment OCTAL_ESC: '\\'('0' .. '3')('0' .. '7')('0' .. '7') | '\\'('0' .. '7')('0' .. '7') | '\\'('0' .. '7') ;
fragment UNICODE_ESC: '\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT ;



