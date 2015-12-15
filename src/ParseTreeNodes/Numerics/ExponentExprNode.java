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
public class ExponentExprNode extends ParseTreeNodes.BinaryExprNode{
    public ExponentExprNode(ParseTreeNodes.ParseTreeNode left, ParseTreeNodes.ParseTreeNode right) {
        super(left, right);
    }
    @Override
    public String OpString() {
        return "Raise";
    }
    @Override
    public String OpName() {
        return "^";
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.EXPONENT;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
        //determine the type of the left and the right node. if they yeild NumLitInt, i can produce a numlitint
        return new ParseTreeNodes.ParseTreeNodeResult(Math.pow((double)leftNode.Evaluate().Result(), (double)rightNode.Evaluate().Result()) ,_Type);
    } 
}
