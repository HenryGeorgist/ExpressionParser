/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Strings;

/**
 *
 * @author Will_and_Sara
 */
public class ConcatenateExprNode extends ParseTreeNodes.BinaryExprNode{
    public ConcatenateExprNode(ParseTreeNodes.ParseTreeNode left, ParseTreeNodes.ParseTreeNode right) {
        super(left, right);
    }
    @Override
    public String OpString() {
        return "Concatenate";
    }
    @Override
    public String OpName() {
        return "&";
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.ANDPERSTAND;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
                //determine the type of the left and the right node. if they yeild NumLitInt, i can produce a numlitint
        if(_Type!=ParseTreeNodes.TypeEnum.ERR){
            return new ParseTreeNodes.ParseTreeNodeResult(leftNode.Evaluate().Result().toString() + rightNode.Evaluate().Result().toString(),ParseTreeNodes.TypeEnum.STRING);
        }else{
            //neither whole or decimal... cannot output?
           return new ParseTreeNodes.ParseTreeNodeResult("Error",ParseTreeNodes.TypeEnum.ERR);
        }
    } 
}
