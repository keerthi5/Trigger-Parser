This is parser for triggers in pl-sql.
There are two files trigger.l and trigger.y.trigger.l specifies the lex command specification file that defines the lexical analysis rules for a trigger while trigger.y specifies the yacc command grammar file that defines the parsing rules, and calls the yylex subroutine created by the lex command to provide input.

Gui.java is a simple java program as a simple interface for this function.

Compile and interpret the java file to use this without the commands of lex and yacc 
or
Use these commands to compile and run trigger.l and trigger.y files

      lex trigger.l
      yacc trigger.y
      gcc -o trigger y.tab.c
      ./trigger
