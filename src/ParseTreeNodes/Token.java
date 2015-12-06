/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes;

/**
 *
 * @author Will_and_Sara
 */
public class Token {
    private Tokens _Token;
    private String _String;
    private int _Line;
    private int _Position;
    public Tokens GetToken(){return _Token;}
    public String GetString(){return _String;}
    public int GetLineNumber(){return _Line;}
    public int GetPosition(){return _Position;}
    public Token(Tokens T,String S,int Line,int Pos){
        _Token = T;
        _String = S;
        _Line = Line;
        _Position = Pos;
    }
    public String ToString(){return "Token(" + _Token + ", " + _String + ")";}
}
