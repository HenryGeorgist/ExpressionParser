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
public class SubStringExprNode extends ParseTreeNodes.ParseTreeNode implements ParseTreeNodes.IDisplayToTreeNode{
    private ParseTreeNodes.ParseTreeNode _MainString;
    private ParseTreeNodes.ParseTreeNode _StartPos;
    private ParseTreeNodes.ParseTreeNode _NumChar;
    public SubStringExprNode(){
        //for reflection
    }
    public SubStringExprNode(ParseTreeNodes.ParseTreeNode string, ParseTreeNodes.ParseTreeNode StartPos, ParseTreeNodes.ParseTreeNode NumCharacters){
        super();
        if(string == null){
            _SyntaxErrors.add("The string argument in the expression SubString was not defined");
            _Type = ParseTreeNodes.TypeEnum.ERR;
        }else{
            if(string.Type() == ParseTreeNodes.TypeEnum.STRING){
                _MainString = string;
                if(StartPos == null){
                    _SyntaxErrors.add("The start posistion argument in the expression SubString was not defined");
                    _Type = ParseTreeNodes.TypeEnum.ERR;
                }else{
                    if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(StartPos.Type())){
                        _StartPos = StartPos;
                        if(NumCharacters == null){
                            _SyntaxErrors.add("The number of characters argument in the expression SubString was not defined");
                            _Type = ParseTreeNodes.TypeEnum.ERR;
                        }else{
                            if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(NumCharacters.Type())){
                                _NumChar = NumCharacters;
                                _Type = ParseTreeNodes.TypeEnum.STRING; 
                            }else{
                                _SyntaxErrors.add("The number of characters argument in the expression SubString does not produce a whole number.");
                                _Type = ParseTreeNodes.TypeEnum.ERR;
                            }     
                        }
                    }else{
                        _SyntaxErrors.add("The start position argument in the expression Substring does not produce a whole number.");
                        _Type = ParseTreeNodes.TypeEnum.ERR;
                        //what to do about numchar now??
                    }            
                }
            }else{
                _SyntaxErrors.add("The string argument of the expression Substring does not produce a string result.");
                _Type = ParseTreeNodes.TypeEnum.ERR;
                //what about the other two arguments?
            }
        }
    }
    @Override
    public Tokens Token() {return ParseTreeNodes.Tokens.SUBSTRING;}
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
        _StartPos.Update(row);
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
        String sp;
        if(_StartPos == null){
            sp = "";
        }else{
            sp = _StartPos.ToString();
        }
        String nc;
        if(_NumChar == null){
            nc = "";
        }else{
            nc = _NumChar.ToString();
        }
        return "SubString(" + ms + ", " + sp + ", " + nc + ")";
    }
    @Override
    public boolean ContainsVariable() {
        //what if they are null?
        if(_MainString.ContainsVariable()){
            return true;
        }else if(_StartPos.ContainsVariable()){
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
        Errors.addAll(_StartPos.GetComputeErrors());
        Errors.addAll(_NumChar.GetComputeErrors());
        return Errors;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_SyntaxErrors);
        if(null!=_MainString){Errors.addAll(_MainString.GetSyntaxErrors());}
        if(null!=_StartPos){Errors.addAll(_StartPos.GetSyntaxErrors());}
        if(null!=_NumChar){Errors.addAll(_NumChar.GetSyntaxErrors());}
        return Errors;    
    }
    @Override
    public String DisplayName() {return "SubString";}
    @Override
    public String HelpFilePath() {return "www.java.com";}
    @Override
    public String Syntax() {return "SubString(,,)";}
}
