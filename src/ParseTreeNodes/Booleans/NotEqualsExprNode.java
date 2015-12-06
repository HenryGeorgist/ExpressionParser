/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Booleans;

/**
 *
 * @author Will_and_Sara
 */
public class NotEqualsExprNode extends ParseTreeNodes.BinaryExprNode{
    public NotEqualsExprNode(ParseTreeNodes.ParseTreeNode left, ParseTreeNodes.ParseTreeNode right) {
        super(left, right);
    }
    @Override
    public String OpString() {
        return "Compare";
    }
    @Override
    public String OpName() {
        return "!=";
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.NEQ;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
                //determine the type of the left and the right node.
        if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(leftNode.Type()) && ParseTreeNodes.TypeEnum.NUMERICAL.contains(rightNode.Type())){
            return new ParseTreeNodes.ParseTreeNodeResult(Double.parseDouble(leftNode.Evaluate().Result().toString()) != Double.parseDouble(rightNode.Evaluate().Result().toString()),ParseTreeNodes.TypeEnum.BOOLEAN);
          }else if(ParseTreeNodes.TypeEnum.STRING == leftNode.Type() && ParseTreeNodes.TypeEnum.STRING == rightNode.Type()){
              return new ParseTreeNodes.ParseTreeNodeResult(!leftNode.Evaluate().Result().toString().equals(rightNode.Evaluate().Result().toString()), _Type);
          }else{
            //not numerical... cannot output?
           return new ParseTreeNodes.ParseTreeNodeResult(false,ParseTreeNodes.TypeEnum.ERR);
        }
    }
}
