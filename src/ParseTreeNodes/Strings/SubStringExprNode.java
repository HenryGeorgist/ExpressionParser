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
public class SubStringExprNode extends ParseTreeNodes.ParseTreeNode{
    private ParseTreeNodes.ParseTreeNode _MainString;
    private ParseTreeNodes.ParseTreeNode _StartPos;
    private ParseTreeNodes.ParseTreeNode _NumChar;
    public SubStringExprNode(ParseTreeNodes.ParseTreeNode string, ParseTreeNodes.ParseTreeNode StartPos, ParseTreeNodes.ParseTreeNode NumCharacters){
        //check for null values.
        if(string.Type() == ParseTreeNodes.TypeEnum.STRING){
            _MainString = string;

            if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(StartPos.Type())){
                _StartPos = StartPos;
                if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(NumCharacters.Type())){
                   _NumChar = NumCharacters;
                   _Type = ParseTreeNodes.TypeEnum.STRING; 
                }  
            }
        }
    }
    @Override
    public Tokens Token() {
        return ParseTreeNodes.Tokens.SUBSTRING;
    }
    @Override
    public ParseTreeNodeResult Evaluate() {
        String s = _MainString.Evaluate().Result().toString();
        int sp = Integer.parseInt(_StartPos.Evaluate().Result().toString());
        int n = Integer.parseInt(_NumChar.Evaluate().Result().toString());
        if (s.length()<n){
            return new ParseTreeNodes.ParseTreeNodeResult(s,_Type);
        }else{
            return new ParseTreeNodes.ParseTreeNodeResult(s.substring(sp,sp+n),_Type);
        }
    }
    @Override
    public void Update(Object[] row) {
        _MainString.Update(row);
        _NumChar.Update(row);
    }
    @Override
    public String ToString() {
        return "SubString(" + _MainString.ToString() + ", " + _StartPos.ToString() + ", " + _NumChar.ToString() + ")";
    }
    @Override
    public void SetColumnNumbers(String[] ColumnNames) {
        _MainString.SetColumnNumbers(ColumnNames);
        _StartPos.SetColumnNumbers(ColumnNames);
        _NumChar.SetColumnNumbers(ColumnNames);
    }
    @Override
    public boolean ContainsVariable() {
        if(_MainString.ContainsVariable()){
            return true;
        }else if(_StartPos.ContainsVariable()){
            return true;
        }else{
            return _NumChar.ContainsVariable();
        }
    }
}
