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
public class OrExprNode extends ParseTreeNodes.BinaryExprNode{
    public OrExprNode(ParseTreeNodes.ParseTreeNode left, ParseTreeNodes.ParseTreeNode right) {
        super(left, right);
    }
    @Override
    public String OpString() {
        return "Compare";
    }
    @Override
    public String OpName() {
        return "Or";
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.OR;
    }
    @Override
    public String ToString(){
        return "Or(" + leftNode.ToString() + ", " + rightNode.ToString() + ")";
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
                //determine the type of the left and the right node.
        if(Type() == ParseTreeNodes.TypeEnum.BOOLEAN){
            if (Boolean.parseBoolean(leftNode.Evaluate().Result().toString())){
                return new ParseTreeNodes.ParseTreeNodeResult(true,ParseTreeNodes.TypeEnum.BOOLEAN);
            }else{
                return new ParseTreeNodes.ParseTreeNodeResult(Boolean.parseBoolean(rightNode.Evaluate().Result().toString()),ParseTreeNodes.TypeEnum.BOOLEAN);
            }
          }else{
            //not Boolean... cannot output?
           return new ParseTreeNodes.ParseTreeNodeResult(false,ParseTreeNodes.TypeEnum.ERR);
        }
    }   
}
