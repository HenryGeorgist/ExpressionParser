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
public class LeftExprNode extends ParseTreeNodes.ParseTreeNode{
    private ParseTreeNodes.ParseTreeNode MainString;
    private ParseTreeNodes.ParseTreeNode NumChar;
    public LeftExprNode(ParseTreeNodes.ParseTreeNode string, ParseTreeNodes.ParseTreeNode NumCharacters){
        super();
        if(string==null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The string argument of the Left function was not defined.");
        }else{
            if(string.Type() == ParseTreeNodes.TypeEnum.STRING){
            MainString = string;
                if(NumCharacters==null){
                    _Type = ParseTreeNodes.TypeEnum.ERR;
                    _SyntaxErrors.add("The number of characters argument of the Left function was not defined.");
                }else{
                    if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(NumCharacters.Type())){
                        NumChar = NumCharacters;
                        _Type = ParseTreeNodes.TypeEnum.STRING;
                    }else{
                        _Type = ParseTreeNodes.TypeEnum.ERR;
                        _SyntaxErrors.add("The number of characters argument of the Left function does not produce an integer result.");
                    }                
                }
            }else{
                _Type = ParseTreeNodes.TypeEnum.ERR;
                _SyntaxErrors.add("The string argument of the Left function does not produce a string as the output type.");
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
        String ms;
        if(MainString == null){
            ms = "";
        }else{
            ms = MainString.ToString();
        }
        String nc;
        if(NumChar == null){
            nc = "";
        }else{
            nc = NumChar.ToString();
        }
        return "Left(" + ms + ", " + nc + ")";
    }
    @Override
    public boolean ContainsVariable() {
        if(MainString.ContainsVariable()){
            return true;
        }else{
            return NumChar.ContainsVariable();
        }
    }
    @Override
    public ArrayList<String> GetComputeErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_ComputeErrors);
        Errors.addAll(MainString.GetComputeErrors());
        Errors.addAll(NumChar.GetComputeErrors());
        return Errors;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_SyntaxErrors);
        Errors.addAll(MainString.GetSyntaxErrors());
        Errors.addAll(NumChar.GetSyntaxErrors());
        return Errors;    
    }
}
