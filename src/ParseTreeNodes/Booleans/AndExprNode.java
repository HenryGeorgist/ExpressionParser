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
public class AndExprNode extends ParseTreeNodes.BinaryExprNode implements ParseTreeNodes.IDisplayToTreeNode{
    public AndExprNode(){
        super(null,null);
    }
    public AndExprNode(ParseTreeNodes.ParseTreeNode left, ParseTreeNodes.ParseTreeNode right) {
        super(left, right);
    }
    @Override
    public String OpString() {
        return "Compare";
    }
    @Override
    public String OpName() {
        return "And";
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.AND;
    }
    @Override
    public String ToString(){
        return "And(" + leftNode.ToString() + ", " + rightNode.ToString() + ")";
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
                //determine the type of the left and the right node.
        if(Type() == ParseTreeNodes.TypeEnum.BOOLEAN){
            return new ParseTreeNodes.ParseTreeNodeResult(Boolean.parseBoolean(leftNode.Evaluate().Result().toString()) && Boolean.parseBoolean(rightNode.Evaluate().Result().toString()),ParseTreeNodes.TypeEnum.BOOLEAN);
          }else{
            //not Boolean... cannot output?
           return new ParseTreeNodes.ParseTreeNodeResult(false,ParseTreeNodes.TypeEnum.ERR);
        }
    }
    @Override
    public String DisplayName() {return "AND";}
    @Override
    public String HelpFilePath() {return "www.gmail.com";}
    @Override
    public String Syntax() {return "And(,)";}
}
