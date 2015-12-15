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
public abstract class BinaryExprNode extends ParseTreeNode {
    protected ParseTreeNode leftNode;
    protected ParseTreeNode rightNode;
    public abstract String OpString();
    public abstract String OpName();
    public BinaryExprNode(ParseTreeNode left, ParseTreeNode right){
        leftNode = left;
        rightNode = right;
        //do validation and set type.
        if(leftNode==null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The left side of the operator " + OpName() + " is not defined.");
        }else if(rightNode==null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The right side of the operator " + OpName() + " is not defined.");
        }else{
            if(leftNode.Type() == rightNode.Type()){
                //if the operator is boolean the output type will be boolean
                if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(leftNode.Type())){
                    if(ParseTreeNodes.Tokens.BOOLEANOPERATOR.contains(Token())){
                        _Type = ParseTreeNodes.TypeEnum.BOOLEAN;
                    }else{
                        _Type = leftNode.Type();
                    }
                    
                }else if(ParseTreeNodes.TypeEnum.BOOLEAN == leftNode.Type()){ 
                _Type = ParseTreeNodes.TypeEnum.BOOLEAN;
                }else{
                //both sides not numerical must be strings
                //only allow &
                if (OpName()=="&"){
                    //concatenation, equals, or not equals
                    _Type = ParseTreeNodes.TypeEnum.STRING;
                }else if(OpName()=="=" || OpName()=="!="){
                    _Type = ParseTreeNodes.TypeEnum.BOOLEAN;
                }else{
                    //not acceptable operation
                    _Type = ParseTreeNodes.TypeEnum.ERR;
                    _SyntaxErrors.add("Cannot " + OpString() + " " + leftNode.Type() + " and " + rightNode.Type() + ", operation: " + leftNode.ToString() + " " + OpName() + " " + rightNode.ToString() + " is invalid.");
                }
                }
                
            }else if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(leftNode.Type()) && ParseTreeNodes.TypeEnum.NUMERICAL.contains(rightNode.Type())){
                //numerical
                _Type = ParseTreeNodes.TypeEnum.DOUBLE;
                
            }else{
                //one side is not numerical must be strings
                //only allow and &
                if (OpName()=="&"){
                    //concatenation
                    _Type = ParseTreeNodes.TypeEnum.STRING;
                }else{
                    //not acceptable operation
                    _Type = ParseTreeNodes.TypeEnum.ERR;
                    _SyntaxErrors.add("Cannot " + OpString() + " " + leftNode.Type() + " and " + rightNode.Type() + ", operation: " + leftNode.ToString() + " " + OpName() + " " + rightNode.ToString() + " is invalid.");
                }
            }
        }
    }
    @Override
    public void Update(Object[] row) {
        leftNode.Update(row);
        rightNode.Update(row);
    }
    @Override
    public String ToString() {
        return "(" + leftNode.ToString()+ " " + OpName() + " " + rightNode.ToString() + ")";
    }
    @Override
    public boolean ContainsVariable() {
        return (leftNode.ContainsVariable()||rightNode.ContainsVariable());
    }
    @Override
    public abstract Tokens Token();
    @Override
    public abstract ParseTreeNodeResult Evaluate();
}
