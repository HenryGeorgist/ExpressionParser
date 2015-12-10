/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes;

/**
 *
 * @author Will_and_Sara
 */
public abstract class ParseTreeNode {
    protected TypeEnum _Type;
    public abstract Tokens Token();
    public abstract ParseTreeNodeResult Evaluate();
    public abstract void Update(Object[] row);
    public abstract String ToString();
    public abstract boolean ContainsVariable();
    public TypeEnum Type(){return _Type;}
}
