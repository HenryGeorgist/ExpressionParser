/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Strings;

import ParseTreeNodes.ParseTreeNodeResult;
import ParseTreeNodes.Tokens;

/**
 *
 * @author Will_and_Sara
 */
public class LeftExprNode extends ParseTreeNodes.ParseTreeNode{
    private ParseTreeNodes.ParseTreeNode MainString;
    private ParseTreeNodes.ParseTreeNode NumChar;
    public LeftExprNode(ParseTreeNodes.ParseTreeNode string, ParseTreeNodes.ParseTreeNode NumCharacters){
        if(string.Type() == ParseTreeNodes.TypeEnum.STRING){
            MainString = string;
            if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(NumCharacters.Type())){
                NumChar = NumCharacters;
                _Type = ParseTreeNodes.TypeEnum.STRING;
            }
        }
    }
    @Override
    public Tokens Token() {
        return ParseTreeNodes.Tokens.LEFT;
    }
    @Override
    public ParseTreeNodeResult Evaluate() {
        String s = MainString.Evaluate().Result().toString();
        int n = Integer.parseInt(NumChar.Evaluate().Result().toString());
        if (s.length()<n){
            return new ParseTreeNodes.ParseTreeNodeResult(s,_Type);
        }else{
            return new ParseTreeNodes.ParseTreeNodeResult(s.substring(0,n),_Type);
        }
    }
    @Override
    public void Update(Object[] row) {
        MainString.Update(row);
        NumChar.Update(row);
    }
    @Override
    public String ToString() {
        return "Left(" + MainString.ToString() + ", " + NumChar.ToString() + ")";
    }
    @Override
    public boolean ContainsVariable() {
        if(MainString.ContainsVariable()){
            return true;
        }else{
            return NumChar.ContainsVariable();
        }
    } 
}
