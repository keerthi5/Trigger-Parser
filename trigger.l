%{
#include<stdlib.h>
void yyerror(char*);
#include"y.tab.h"
%}
alpha [A-Za-z]
digit [0-9]
%%
[\t \n]
create return CREATE;
"or replace" return REPLACE;
trigger return TRIGGER;
before return BEFORE;
after return AFTER;
when return WHEN;
"instead of" return INSTEAD;
insert return INSERT;
update return UPDATE;
delete return DELETE;
of return OF;
on return ON;
"for each row" return FOR;
declare return DECLARE;
exception return EXCEPTION;
end return END;
{digit}+ return NUM;
{alpha}({alpha}|{digit})* return ID;
"<=" return LE;
">=" return GE;
"==" return EQ;
"!=" return NE;
. return yytext[0];
%% 
int yywrap()
{
   return 1;
}
