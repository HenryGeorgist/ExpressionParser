/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Variables;

import ParseTreeNodes.ParseTreeNodeResult;
import ParseTreeNodes.Tokens;

/**
 *
 * @author Will_and_Sara
 */
public class NormInvExprNode extends ParseTreeNodes.ParseTreeNode{
    private ParseTreeNodes.ParseTreeNode _Mean;
    private ParseTreeNodes.ParseTreeNode _StDev;
    private ParseTreeNodes.ParseTreeNode _Seed;
    private java.util.Random _Rand;
    public void NormInvExprNode(ParseTreeNodes.ParseTreeNode mean, ParseTreeNodes.ParseTreeNode stdev){
        
    }
    public void NormInvExprNode(ParseTreeNodes.ParseTreeNode seed){
        _Mean = new ParseTreeNodes.Numerics.NumNode(0.0);
        _StDev = new ParseTreeNodes.Numerics.NumNode(1.0);
        if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(seed.Type())){
            _Seed = seed;
            _Rand = new java.util.Random((long)_Seed.Evaluate().Result());
            _Type = ParseTreeNodes.TypeEnum.DOUBLE;
        }else{
            //error
            _Type = ParseTreeNodes.TypeEnum.DOUBLE;
            _Rand = new java.util.Random();
        }
    }
    public void NormInvExprNode(){
        _Mean = new ParseTreeNodes.Numerics.NumNode(0.0);
        _StDev = new ParseTreeNodes.Numerics.NumNode(1.0);
        _Type = ParseTreeNodes.TypeEnum.DOUBLE;
        _Rand = new java.util.Random();
    }
    public void NormInvExprNode(ParseTreeNodes.ParseTreeNode mean, ParseTreeNodes.ParseTreeNode stdev, ParseTreeNodes.ParseTreeNode seed){
        //check for null nodes and bad input types...
        _Mean = mean;
        _StDev = stdev;
        _Seed = seed;
        _Rand = new java.util.Random((long)_Seed.Evaluate().Result());
    }
    @Override
    public Tokens Token() {
        return ParseTreeNodes.Tokens.NORMINV;
    }
    @Override
    public ParseTreeNodeResult Evaluate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Update(Object[] row) {
        _Mean.Update(row);
        _StDev.Update(row);
        _Seed.Update(row);
    }

    @Override
    public String ToString() {
        //check for nulls.
        return "NormInv(" + _Mean.ToString() + ", " + _StDev.ToString() + ", " + _Seed.ToString() + ")";
    }
    @Override
    public boolean ContainsVariable() {return true;}
    
}
