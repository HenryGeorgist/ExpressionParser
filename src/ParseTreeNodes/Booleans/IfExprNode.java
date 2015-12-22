/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Booleans;

import ParseTreeNodes.ParseTreeNodeResult;
import ParseTreeNodes.Tokens;
import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public class IfExprNode extends ParseTreeNodes.ParseTreeNode {
    private ParseTreeNodes.ParseTreeNode _Condition;
    private ParseTreeNodes.ParseTreeNode _Then;
    private ParseTreeNodes.ParseTreeNode _Else;
    public IfExprNode(ParseTreeNodes.ParseTreeNode Condition){
        super();
        //must produce a boolean
        if(Condition == null){
            _SyntaxErrors.add("The condition in an if statement is not defined");
        }else{
            if(Condition.Type()==ParseTreeNodes.TypeEnum.BOOLEAN){
                _Condition = Condition;
            }else{
                _SyntaxErrors.add("The condition " + Condition.ToString() + " is used in an if statement, and does not produce a boolean.");
                _Type = ParseTreeNodes.TypeEnum.ERR;
            }
        }
    }
    public void SetThen(ParseTreeNodes.ParseTreeNode ThenStatement){
        //can be any type
        if(ThenStatement == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The then expression in an if statment is not defined");
        }else{
            _Type = ThenStatement.Type();
            _Then = ThenStatement;
        }   
    }
    public void SetElse(ParseTreeNodes.ParseTreeNode ElseStatement){
        if(ElseStatement == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The else statement in an if statment is not defined");
        }else{
            if(_Then==null){
                _Else = ElseStatement;
                _Type = ElseStatement.Type();
                _SyntaxErrors.add("The then statment in an if statement is not defined.");             
            }else{
                if(_Then.Type()==ElseStatement.Type()){
                    _Else = ElseStatement;
                }else{
                    _Type = ParseTreeNodes.TypeEnum.ERR;
                    _SyntaxErrors.add("The else statment and the then statment in an if statement do not produce the same output type.");
                }
            }
        }
    }
    @Override
    public Tokens Token() {return ParseTreeNodes.Tokens.IF;}
    @Override
    public ParseTreeNodeResult Evaluate() {
        if(Boolean.parseBoolean(_Condition.Evaluate().Result().toString())){
            return new ParseTreeNodes.ParseTreeNodeResult(_Then.Evaluate().Result().toString(),_Type);
        }
        return new ParseTreeNodes.ParseTreeNodeResult(_Else.Evaluate().Result().toString(),_Type);
    }
    @Override
    public void Update(Object[] row) {
        _Condition.Update(row);
        _Then.Update(row);
        _Else.Update(row);
    }
    @Override
    public String ToString() {
        //check if each element is not nothing
        if(_Condition==null){
            return "IF(Error , Error , Error)";
        }else if(_Then==null){
            return "IF(" + _Condition.ToString() + " , Error , Error)"; 
        }else if(_Else==null){
          return "IF(" + _Condition.ToString() + " , " + _Then.ToString() + " , Error)"; 
        }else{
          return "IF(" + _Condition.ToString() + " , " + _Then.ToString() + " , " + _Else.ToString() + ")";  
        }
    }
    @Override
    public boolean ContainsVariable() {
        if(_Condition.ContainsVariable()){
            return true;
        }else if(_Then.ContainsVariable()){
            return true;
        }else{
            return _Else.ContainsVariable();
        }
    }
    @Override
    public ArrayList<String> GetComputeErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_ComputeErrors);
        Errors.addAll(_Condition.GetComputeErrors());
        Errors.addAll(_Then.GetComputeErrors());
        Errors.addAll(_Else.GetComputeErrors());
        return Errors;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_SyntaxErrors);
        if(null!=_Condition){Errors.addAll(_Condition.GetSyntaxErrors());}
        if(null!=_Then){Errors.addAll(_Then.GetSyntaxErrors());}
        if(null!=_Else){Errors.addAll(_Else.GetSyntaxErrors());}
        return Errors;    
    }
}
