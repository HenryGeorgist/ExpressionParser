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
public class NumNode extends ParseTreeNodes.ParseTreeNode {
    private Double _d;
    public NumNode(Double d){
        super();
        _d = d;
        _Type = ParseTreeNodes.TypeEnum.DOUBLE;
        
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.NUMLIT;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
        return new ParseTreeNodes.ParseTreeNodeResult(_d, _Type);
    }
    @Override
    public void Update(Object[] row) {
        //do nothing, this is not a variable
    }
    @Override
    public String ToString() {
        return _d.toString();
    }
    @Override
    public boolean ContainsVariable() {
        return false;
    }
    @Override
    public ArrayList<String> GetComputeErrors() {
        return _ComputeErrors;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        return _SyntaxErrors;
    }
}
