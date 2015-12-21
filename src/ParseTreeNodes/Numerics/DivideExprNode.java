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
        _Type = ParseTreeNodes.TypeEnum.DOUBLE;
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
        double numerator = Double.parseDouble(leftNode.Evaluate().Result().toString());
        double denominator = Double.parseDouble(rightNode.Evaluate().Result().toString());
        if(denominator != 0){
            return new ParseTreeNodes.ParseTreeNodeResult(numerator / denominator,_Type);
        }else{
            _ComputeErrors.add("Divide by zero Error on row (or cell) " + _RowOrCellNum);
            return new ParseTreeNodes.ParseTreeNodeResult(0, _Type);///technically this is as close to infinity i can get...
        }
        
    }   
}
