/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Numerics;

/**
 *
 * @author Will_and_Sara
 */
public class NumIntNode extends ParseTreeNodes.ParseTreeNode {
    private int _I;
    public NumIntNode(int I){
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
        return new Integer(_I).toString();
    }
    @Override
    public boolean ContainsVariable() {
        return false;
    }  
}
