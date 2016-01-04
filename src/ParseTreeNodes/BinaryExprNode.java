/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes;

import java.util.ArrayList;

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
        super();
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
                    }else if(Token()==ParseTreeNodes.Tokens.EXPONENT){
                        _Type = ParseTreeNodes.TypeEnum.DOUBLE;
                    }else{
                        _Type = leftNode.Type();
                    }
                    
                }else if(ParseTreeNodes.TypeEnum.BOOLEAN == leftNode.Type()){ 
                _Type = ParseTreeNodes.TypeEnum.BOOLEAN;
                }else{
                //both sides not numerical must be strings 
                //only allow &
                if ("&".equals(OpName())){
                    //concatenation, equals, or not equals
                    _Type = ParseTreeNodes.TypeEnum.STRING;
                }else if("=".equals(OpName()) || "!=".equals(OpName())){
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
                //one side is not numerical must be strings (or one side is an error.)
                //only allow and &
                if (OpName().equals("&")){
                    //concatenation
                    _Type = ParseTreeNodes.TypeEnum.STRING;
                }else if(leftNode.Type()!=ParseTreeNodes.TypeEnum.ERR & rightNode.Type()==ParseTreeNodes.TypeEnum.ERR){
                    _Type = ParseTreeNodes.TypeEnum.ERR;   
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
        String ln = "";
        String rn = "";
        if(null!=leftNode){ln = leftNode.ToString();}
        if(null!=rightNode){rn = rightNode.ToString();}
        return "(" + ln + " " + OpName() + " " + rn + ")";
    }
    @Override
    public boolean ContainsVariable() {
        return (leftNode.ContainsVariable()||rightNode.ContainsVariable());
    }
    @Override
    public abstract Tokens Token();
    @Override
    public abstract ParseTreeNodeResult Evaluate();

    @Override
    public ArrayList<String> GetComputeErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_ComputeErrors);
        Errors.addAll(leftNode.GetComputeErrors());
        Errors.addAll(rightNode.GetComputeErrors());
        return Errors;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_SyntaxErrors);
        if(null!=leftNode){Errors.addAll(leftNode.GetSyntaxErrors());}
        if(null!=rightNode){Errors.addAll(rightNode.GetSyntaxErrors());}
        return Errors;
    }
}
