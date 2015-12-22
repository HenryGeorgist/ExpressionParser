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
    private ParseTreeNodes.ParseTreeNode _MainString;
    private ParseTreeNodes.ParseTreeNode _NumChar;
    public LeftExprNode(ParseTreeNodes.ParseTreeNode string, ParseTreeNodes.ParseTreeNode NumCharacters){
        super();
        if(string==null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The string argument of the Left function was not defined.");
        }else{
            if(string.Type() == ParseTreeNodes.TypeEnum.STRING){
            _MainString = string;
                if(NumCharacters==null){
                    _Type = ParseTreeNodes.TypeEnum.ERR;
                    _SyntaxErrors.add("The number of characters argument of the Left function was not defined.");
                }else{
                    if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(NumCharacters.Type())){
                        _NumChar = NumCharacters;
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
        String s = _MainString.Evaluate().Result().toString();
        int n = Integer.parseInt(_NumChar.Evaluate().Result().toString());
        if (s.length()<n){
            return new ParseTreeNodes.ParseTreeNodeResult(s,_Type);
        }else{
            return new ParseTreeNodes.ParseTreeNodeResult(s.substring(0,n),_Type);
        }
    }
    @Override
    public void Update(Object[] row) {
        _MainString.Update(row);
        _NumChar.Update(row);
    }
    @Override
    public String ToString() {
        String ms;
        if(_MainString == null){
            ms = "";
        }else{
            ms = _MainString.ToString();
        }
        String nc;
        if(_NumChar == null){
            nc = "";
        }else{
            nc = _NumChar.ToString();
        }
        return "Left(" + ms + ", " + nc + ")";
    }
    @Override
    public boolean ContainsVariable() {
        if(_MainString.ContainsVariable()){
            return true;
        }else{
            return _NumChar.ContainsVariable();
        }
    }
    @Override
    public ArrayList<String> GetComputeErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_ComputeErrors);
        Errors.addAll(_MainString.GetComputeErrors());
        Errors.addAll(_NumChar.GetComputeErrors());
        return Errors;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_SyntaxErrors);
        if(null!=_MainString){Errors.addAll(_MainString.GetSyntaxErrors());}
        if(null!=_NumChar){Errors.addAll(_NumChar.GetSyntaxErrors());}
        return Errors;    
    }
}
