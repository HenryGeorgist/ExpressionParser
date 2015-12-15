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
public class PlusExprNode extends ParseTreeNodes.BinaryExprNode{
    public PlusExprNode(ParseTreeNodes.ParseTreeNode left, ParseTreeNodes.ParseTreeNode right) {
        super(left, right);
    }
    @Override
    public String OpString() {
        return "Add";
    }
    @Override
    public String OpName() {
        return "+";
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.PLUS;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
        //determine the type of the left and the right node. if they yeild NumLitInt, i can produce a numlitint
        if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(_Type) ){
            return new ParseTreeNodes.ParseTreeNodeResult((int)leftNode.Evaluate().Result() + (int)rightNode.Evaluate().Result(),_Type);
        }else{
            return new ParseTreeNodes.ParseTreeNodeResult((double)leftNode.Evaluate().Result() + (double)rightNode.Evaluate().Result(),_Type);
        }
    }   
}
