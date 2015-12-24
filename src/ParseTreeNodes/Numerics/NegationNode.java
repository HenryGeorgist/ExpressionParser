/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Numerics;

import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public class NegationNode extends ParseTreeNodes.ParseTreeNode {
    private ParseTreeNodes.ParseTreeNode _d;
    public NegationNode(ParseTreeNodes.ParseTreeNode d){
        super();
        _d = d;
        if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(_d.Type())){
          _Type = _d.Type();  
        }else{
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("A minus sign was followed by something that does not produce a numerical output");
        }
        
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.NUMLIT;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
        //return new ParseTreeNodes.ParseTreeNodeResult(-Double.parseDouble(_d.Evaluate().Result().toString()), _Type);
        if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(_Type)){
            return new ParseTreeNodes.ParseTreeNodeResult(-Integer.parseInt(_d.Evaluate().Result().toString()), _Type);
        }else{
            return new ParseTreeNodes.ParseTreeNodeResult(-Double.parseDouble(_d.Evaluate().Result().toString()), _Type);
        }   
    }
    @Override
    public void Update(Object[] row) {
        _d.Update(row);
    }
    @Override
    public String ToString() {
        return "(-" + _d.ToString() + ")";
    }
    @Override
    public boolean ContainsVariable() {
        return false;
    }
    @Override
    public ArrayList<String> GetComputeErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_ComputeErrors);
        Errors.addAll(_d.GetComputeErrors());
        return Errors;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_SyntaxErrors);
        Errors.addAll(_d.GetSyntaxErrors());
        return Errors;    
    }
}
