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
public class NumIntNode extends ParseTreeNodes.ParseTreeNode {
    private int _I;
    public NumIntNode(int I){
        super();
        _I = I;
        _Type = ParseTreeNodes.TypeEnum.LONG;
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.NUMLITINT;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
        return new ParseTreeNodes.ParseTreeNodeResult(_I, _Type);
    }
    @Override
    public void Update(Object[] row) {
        //do nothing, this is not a variable
    }
    @Override
    public String ToString() {
        return Integer.toString(_I);
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
