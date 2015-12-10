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
public class ContainsExprNode extends ParseTreeNodes.ParseTreeNode{
    private ParseTreeNodes.ParseTreeNode _MainString;
    private ParseTreeNodes.ParseTreeNode _SearchString;
    public ContainsExprNode(ParseTreeNodes.ParseTreeNode MainString, ParseTreeNodes.ParseTreeNode SearchString){
        //Check if they are null...        
        //make sure both are strings?
        if(MainString.Type()==ParseTreeNodes.TypeEnum.STRING && SearchString.Type()==ParseTreeNodes.TypeEnum.STRING){
            _MainString = MainString;
            _SearchString = SearchString;
            _Type = ParseTreeNodes.TypeEnum.BOOLEAN;
        }else{
            //else, errors?
            _Type = ParseTreeNodes.TypeEnum.ERR;
        }
    }
    @Override
    public Tokens Token() {
        return ParseTreeNodes.Tokens.CONTAINS;
    }
    @Override
    public ParseTreeNodeResult Evaluate() {
        return new ParseTreeNodes.ParseTreeNodeResult(_MainString.Evaluate().Result().toString().contains(_SearchString.Evaluate().Result().toString()),_Type);
    }
    @Override
    public void Update(Object[] row) {
        _MainString.Update(row);
        _SearchString.Update(row);
    }
    @Override
    public String ToString() {
        String MS;
        String SS;
        if(_MainString == null){
            MS = "Error";
        }else{ MS = _MainString.ToString();}
        if(_SearchString == null){
            SS = "Error";
        }else{ SS = _SearchString.ToString();}
        return "Contains(" + MS + " , " + SS + ")";
    }
    @Override
    public boolean ContainsVariable() {
        if(_MainString.ContainsVariable()){
            return true;
        }else{
            return _SearchString.ContainsVariable();
        }
    }
    
}
