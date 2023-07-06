Given the grammar defined by the following set of production rules in the EBNF :

project-declaration -> project-def "." __
project-def -> project-heading declarations compound-stmt __
project-heading -> project "name" ";" __
declarations -> const-decl var-decl subroutine-decl __
const-decl -> const ( const-item ";" )+ |  __
const-item -> "name" = "integer-value" __
var-decl -> var (var-item ";" )+ |  __
var-item -> name-list ":" int __
name-list -> "name" ( "," "name" )* __
subroutine-decl -> subroutine-heading declarations compound-stmt “;” |  __
subroutine-heading -> routine "name" ";" __
compund-stmt -> start stmt-list end __
stmt-list -> ( statement ";" )* __
statement -> ass-stmt | inout-stmt | if-stmt | loop-stmt | compound-stmt |  __
ass-stmt ->” name” ":=" arith-exp __
arith-exp -> term ( add-sign term )* __
term -> factor ( mul-sign factor )* __
factor -> "(" arith-exp ")" | name-value __
name-value -> "name" | "integer-value" __
add-sign -> "+" | "-" __
mul-sign -> "*" | "/" | “%” __
inout-stmt -> input "(" "name" ")" | output "(" name-value ")" __
if-stmt -> if “(“ bool-exp “)” then statement else-part endif __
else-part -> else statement |  __
loop-stmt -> loop “(“ bool-exp “)” do statement __
bool-exp -> name-value relational-oper name-value __
relational-oper -> "=" | "<>" | "<" | "<=" | ">" | ">=" __

Notes:
(0) All “names” and “integer-value” are user defined names and values in the source code.
(1) The tokens in bold letters are reserved words.
(2) The words between “ …” are terminals (tokens).

Write an a recursive descent parser for the above grammar.
* You should work individually only, any signs of cheating will be penalized severely.
* Your program will be tested with a random program.
* No programs will be accepted after the deadline for any reason whatsoever.
* In the ERROR function, report the error clearly and precisely showing the line and token where the
* Error occurs and exit the program (panic mode error handling.
* Submit only the source code by replying to the message “439-Project-S22” on Ritaj web page.
