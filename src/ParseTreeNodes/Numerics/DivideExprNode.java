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
public class DivideExprNode extends ParseTreeNodes.BinaryExprNode{
    public DivideExprNode(ParseTreeNodes.ParseTreeNode left, ParseTreeNodes.ParseTreeNode right) {
        super(left, right);
    }
    @Override
    public String OpString() {
        return "Divide";
    }
    @Override
    public String OpName() {
        return "/";
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.DIVIDE;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
        if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(Type())){
            return new ParseTreeNodes.ParseTreeNodeResult(Double.parseDouble(leftNode.Evaluate().Result().toString()) / Double.parseDouble(rightNode.Evaluate().Result().toString()),ParseTreeNodes.TypeEnum.DOUBLE);
        }else{
           return new ParseTreeNodes.ParseTreeNodeResult(0,ParseTreeNodes.TypeEnum.ERR); 
        }
        
    }   
}
