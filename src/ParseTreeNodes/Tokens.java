/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes;
import java.util.EnumSet;

/**
 *
 * @author Will_and_Sara
 */
public enum Tokens {
    LPAREN,
    RPAREN,
    LBRACKET,
    RBRACKET,
    CLBRACKET,
    CRBRACKET,
    COLUMN,
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,
    EXPONENT,
    EQ,
    NEQ,
    LT,
    GT,
    LTE,
    GTE,
    EOF,
    ERR,
    ID,
    IF,
    AND,
    OR,
    NUMLIT,
    NUMLITINT,
    INDEX,
    CONTAINS,
    OFFSET,
    STRINGLIT,
    BOOLLIT,
    COMMA,
    ANDPERSTAND,
    RIGHT,
    LEFT,
    LEN,
    ROUND,
    ROUNDUP,
    ROUNDDOWN,
    RAND,
    RANDINT,
    RANDBETWEEN,
    RANDINTBETWEEN,
    NORMINV,
    TRIINV,
    SUBSTRING,
    INSTRING,
    CONVERTTOINT,
    CONVERTTODOUBLE,
    CONVERTTOSINGLE,
    CONVERTTOSTRING,
    CONVERTTOBOOLEAN,
    CONVERTTOSHORT,
    CONVERTTOBYTE;
    public static final EnumSet<Tokens> EXPR = EnumSet.of(PLUS,MINUS);
    public static final EnumSet<Tokens> TERM = EnumSet.of(TIMES,DIVIDE,EXPONENT);
    public static final EnumSet<Tokens> BOOLEANOPERATOR = EnumSet.of(EQ,LT,GT,LTE,GTE,NEQ,AND,OR);
}
