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
public class LnExprNode extends ParseTreeNodes.ParseTreeNode implements ParseTreeNodes.IDisplayToTreeNode{
    private ParseTreeNodes.ParseTreeNode _X;
    public LnExprNode(){
        //for reflection
    }
    public LnExprNode(ParseTreeNodes.ParseTreeNode x){
        super();
        if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(x.Type())){
            _X = x;
            _Type = ParseTreeNodes.TypeEnum.DOUBLE;
        }else{
            _X = x;
            _SyntaxErrors.add("The value of the argument of the natural Log was not numerical.");
            _Type = ParseTreeNodes.TypeEnum.ERR;
        }
    }
    @Override
    public Tokens Token() {return ParseTreeNodes.Tokens.LN;}
    @Override
    public ParseTreeNodeResult Evaluate() {
        double val = Double.parseDouble(_X.Evaluate().Result().toString());
        return new ParseTreeNodes.ParseTreeNodeResult(Math.log(val),_Type);
    }
    @Override
    public void Update(Object[] row) {_X.Update(row);}
    @Override
    public String ToString() {
        if(_X == null){
            return "LN()";
        }else{
            return "LN(" + _X.ToString() + ")";
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
    public String DisplayName() {return "LN";}
    @Override
    public String HelpFilePath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public String Syntax() {return "LN()";}
}
