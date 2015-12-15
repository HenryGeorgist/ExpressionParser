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
    private boolean _isStandard = false;
    private boolean _hasSeed = false;
    public void NormInvExprNode(ParseTreeNodes.ParseTreeNode mean, ParseTreeNodes.ParseTreeNode stdev){
        if(mean == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The mean argument of the Normal Inverse CDF expression was not defined.");
        }else{
            if(stdev==null){
                _Type = ParseTreeNodes.TypeEnum.ERR;
                _SyntaxErrors.add("The Standard Deviation argument of the Normal Inverse CDF expression was not defined.");
            }else{
                //ensure they are numerical arguments.
                _Mean = mean;
                _StDev = stdev;
                _Rand = new java.util.Random();
                if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(_Mean.Type()) && ParseTreeNodes.TypeEnum.NUMERICAL.contains(_StDev.Type())){
                    _Type = ParseTreeNodes.TypeEnum.DOUBLE;
                }else{
                    _Type = ParseTreeNodes.TypeEnum.ERR;
                    _SyntaxErrors.add("The Mean and Standard Deviation arguments of the Normal Inverse CDF function were not both numerical.");
                }    
            }
        }
    }
    public void NormInvExprNode(ParseTreeNodes.ParseTreeNode seed){
        _Mean = new ParseTreeNodes.Numerics.NumNode(0.0);
        _StDev = new ParseTreeNodes.Numerics.NumNode(1.0);
        if(seed == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The Seed of the standard normal inverse CDF function was not provided.");
        }else{
            if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(seed.Type())){
                _isStandard = true;
                _hasSeed = true;
                _Seed = seed;
                _Rand = new java.util.Random((long)_Seed.Evaluate().Result());
                _Type = ParseTreeNodes.TypeEnum.DOUBLE;
            }else{
                _Type = ParseTreeNodes.TypeEnum.ERR;
                _SyntaxErrors.add("The seed argument in the standard normal inverse CDF function did not produce a whole number.");
            }
        }
    }
    public void NormInvExprNode(){
        _isStandard = true;
        _Mean = new ParseTreeNodes.Numerics.NumNode(0.0);
        _StDev = new ParseTreeNodes.Numerics.NumNode(1.0);
        _Type = ParseTreeNodes.TypeEnum.DOUBLE;
        _Rand = new java.util.Random();
    }
    public void NormInvExprNode(ParseTreeNodes.ParseTreeNode mean, ParseTreeNodes.ParseTreeNode stdev, ParseTreeNodes.ParseTreeNode seed){
        //check for null nodes and bad input types...
        _hasSeed = true;
        _Mean = mean;
        _StDev = stdev;
        _Seed = seed;
        _Rand = new java.util.Random((long)_Seed.Evaluate().Result());
    }
    @Override
    public Tokens Token() {return ParseTreeNodes.Tokens.NORMINV;}
    @Override
    public ParseTreeNodeResult Evaluate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void Update(Object[] row) {
        _Mean.Update(row);
        _StDev.Update(row);
        if(_Seed != null){_Seed.Update(row);}
    }
    @Override
    public String ToString() {
        //check for nulls.
        if(_isStandard){
            if(_hasSeed){
                return "NormInv(" + _Seed.ToString() + ")";
            }else{
                return "NormInv()";
            }
        }else{
            if(_hasSeed){
                return "NormInv(" + _Mean.ToString() + ", " + _StDev.ToString() + ", " + _Seed.ToString() + ")"; 
            }else{
                return "NormInv(" + _Mean.ToString() + ", " + _StDev.ToString() +")"; 
            }
        }  
    }
    @Override
    public boolean ContainsVariable() {return true;}
}
