%{
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
extern FILE* yyin;
int yyerror(char *msg);
int yylex();
%}
%token ID NUM CREATE OR REPLACE TRIGGER BEFORE AFTER INSTEAD INSERT UPDATE DELETE OF ON FOR WHEN LE GE EQ NE DECLARE EXCEPTION END
%right '='
%left AND OR
%left '>' '<' LE GE EQ NE

%%
  
S          : ST ';' {printf("Trigger accepted\n"); exit(0);};

ST         : CREATE TRIGGER name ST1
	   | CREATE REPLACE TRIGGER name ST1
	   | CREATE TRIGGER name ST5
	   | CREATE REPLACE TRIGGER name ST5
           ;

ST1        : BEFORE ST2
 	   | AFTER ST2
	   | INSTEAD ST2
           ;

ST2        : INSERT ST4
	   | UPDATE ST4
           | DELETE ST4
	   | INSERT OR UPDATE ST4
           | INSERT OR DELETE ST4
	   | UPDATE OR DELETE ST4
           | INSERT OR UPDATE OR DELETE ST4
           ;

ST4        : OF name ST5
           |
           ;

ST5        : ON tableList ST6
           ;

ST6        : FOR ST7
           | ST7
           ;

ST7        : WHEN '('COND')' ST8
           ;
 
ST8        : DECLARE declarestatements ST9
           ;

ST9        : name executablestatements ST10
           ;

ST10       : EXCEPTION executablestatements ST11
           | ST11
           ;

ST11       : END
           ;

name       : ID
           ;

declarestatements     : ID' 'declarestatements
                      | ID
                      ;

executablestatements  : ID ';'
                      | ID E ';'
                      | ID COND ';'
                      ;
tableList             : ID',' tableList
                      | ID
                      ;

COND                  : COND OR COND
                      | COND AND COND
                      | E
                      ;
E                     : F '=' F
                      | F '<' F
                      | F '>' F
                      | F LE F
                      | F GE F
                      | F EQ F
                      | F NE F
                      | F OR F
                      | F AND F
                      ;
F                     : ID
                      | NUM
                      ;

%%
void main(int ac,char *av[]) {
	if(ac > 1 && (yyin = fopen(av[1], "r")) == NULL) {
            perror(av[1]);
            exit(1);
        }
    yyparse();
/*
  if(!yyparse()) {
    printf("trigger worked\n");
  } 
else {
    printf("trigger failed\n");
}*/
}

int yyerror(char *msg){
    printf("trigger failed\n");
return 1;
}

