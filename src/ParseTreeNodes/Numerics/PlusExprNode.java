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
            return new ParseTreeNodes.ParseTreeNodeResult(Integer.parseInt(leftNode.Evaluate().Result().toString()) + Integer.parseInt(rightNode.Evaluate().Result().toString()),_Type);
        }else{
            return new ParseTreeNodes.ParseTreeNodeResult(Double.parseDouble(leftNode.Evaluate().Result().toString()) + Double.parseDouble(rightNode.Evaluate().Result().toString()),_Type);
        }
    }   
}
