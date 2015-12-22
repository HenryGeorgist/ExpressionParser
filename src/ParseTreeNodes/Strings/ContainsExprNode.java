/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Strings;

import ParseTreeNodes.ParseTreeNodeResult;
import ParseTreeNodes.Tokens;
import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public class ContainsExprNode extends ParseTreeNodes.ParseTreeNode{
    private ParseTreeNodes.ParseTreeNode _MainString;
    private ParseTreeNodes.ParseTreeNode _SearchString;
    public ContainsExprNode(ParseTreeNodes.ParseTreeNode MainString, ParseTreeNodes.ParseTreeNode SearchString){
        super();
        if(MainString == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The Main string in the Contains Expression is not defined.");
        }else{
            if(SearchString == null){
                _Type = ParseTreeNodes.TypeEnum.ERR;
                _SyntaxErrors.add("The Search String in the Contains Expression is not defined.");
            }else{
                if(MainString.Type()==ParseTreeNodes.TypeEnum.STRING && SearchString.Type()==ParseTreeNodes.TypeEnum.STRING){
                    _MainString = MainString;
                    _SearchString = SearchString;
                    _Type = ParseTreeNodes.TypeEnum.BOOLEAN;
                }else{
                    _SyntaxErrors.add("Cannot evaluate Contains unless both arguments are strings.");
                    _Type = ParseTreeNodes.TypeEnum.ERR;
                }
            }
        }
    }
    @Override
    public Tokens Token() {return ParseTreeNodes.Tokens.CONTAINS;}
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
            MS = "";
        }else{ MS = _MainString.ToString();}
        if(_SearchString == null){
            SS = "";
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
    @Override
    public ArrayList<String> GetComputeErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_ComputeErrors);
        Errors.addAll(_MainString.GetComputeErrors());
        Errors.addAll(_SearchString.GetComputeErrors());
        return Errors;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_SyntaxErrors);
        if(null!=_MainString){Errors.addAll(_MainString.GetSyntaxErrors());}
        if(null!=_SearchString){Errors.addAll(_SearchString.GetSyntaxErrors());}
        return Errors;    
    }
}
