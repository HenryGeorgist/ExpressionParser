/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Numerics;

import ParseTreeNodes.ParseTreeNodeResult;
import ParseTreeNodes.Tokens;
import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public class Log10ExprNode extends ParseTreeNodes.ParseTreeNode implements ParseTreeNodes.IDisplayToTreeNode{
    private ParseTreeNodes.ParseTreeNode _X;
    public Log10ExprNode(){
        //for reflection
    }
    public Log10ExprNode(ParseTreeNodes.ParseTreeNode x){
        super();
        if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(x.Type())){
            _X = x;
            _Type = ParseTreeNodes.TypeEnum.DOUBLE;
        }else{
            _X = x;
            _SyntaxErrors.add("The value of the argument of Log Base 10 was not numerical.");
            _Type = ParseTreeNodes.TypeEnum.ERR;
        }
    }
    @Override
    public Tokens Token() {return ParseTreeNodes.Tokens.LOG10;}
    @Override
    public ParseTreeNodeResult Evaluate() {
        double val = Double.parseDouble(_X.Evaluate().Result().toString());
        return new ParseTreeNodes.ParseTreeNodeResult(Math.log10(val),_Type);
    }
    @Override
    public void Update(Object[] row) {_X.Update(row);}
    @Override
    public String ToString() {
        if(_X == null){
            return "LOG10()";
        }else{
            return "LOG10(" + _X.ToString() + ")";
        }
    }
    @Override
    public boolean ContainsVariable() {return _X.ContainsVariable();}
    @Override
    public ArrayList<String> GetComputeErrors() {
        ArrayList<String> err = new ArrayList<>();
        err.addAll(_X.GetComputeErrors());
        err.addAll(_ComputeErrors);
        return err;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        ArrayList<String> err = new ArrayList<>();
        err.addAll(_X.GetSyntaxErrors());
        err.addAll(_SyntaxErrors);
        return err;
    }
    @Override
    public String DisplayName() {return "LOG10";}
    @Override
    public String HelpFilePath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public String Syntax() {return "LOG10()";}
}
