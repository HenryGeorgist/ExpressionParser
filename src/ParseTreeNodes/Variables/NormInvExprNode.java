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
    private ParseTreeNodes.ParseTreeNode _Rand;
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
                _Rand = new ParseTreeNodes.Variables.RandExprNode();
                if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(_Mean.Type()) && ParseTreeNodes.TypeEnum.NUMERICAL.contains(_StDev.Type())){
                    _Type = ParseTreeNodes.TypeEnum.DOUBLE;
                }else{
                    _Type = ParseTreeNodes.TypeEnum.ERR;
                    _SyntaxErrors.add("The Mean and Standard Deviation arguments of the Normal Inverse CDF function were not both numerical.");
                }    
            }
        }
    }
    public void NormInvExprNode(ParseTreeNodes.ParseTreeNode rand){
        _Mean = new ParseTreeNodes.Numerics.NumNode(0.0);
        _StDev = new ParseTreeNodes.Numerics.NumNode(1.0);
        if(rand == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The random argument of the standard normal inverse CDF function was not provided.");
        }else{
            if(ParseTreeNodes.TypeEnum.DECIMAL.contains(rand.Type())){
                _isStandard = true;
                _hasSeed = true;
                _Rand = rand;
                _Type = ParseTreeNodes.TypeEnum.DOUBLE;
            }else{
                _Type = ParseTreeNodes.TypeEnum.ERR;
                _SyntaxErrors.add("The rand argument in the standard normal inverse CDF function did not produce a decimal number.");
            }
        }
    }
    public void NormInvExprNode(ParseTreeNodes.ParseTreeNode rand, ParseTreeNodes.ParseTreeNode mean, ParseTreeNodes.ParseTreeNode stdev){
        //check for null nodes and bad input types...
        _hasSeed = true;
        _Mean = mean;
        _StDev = stdev;
        _Rand = rand;
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
        _Rand.Update(row);
    }
    @Override
    public String ToString() {
        //check for nulls.
        if(_isStandard){
            if(_hasSeed){
                return "NormInv(" + _Rand.ToString() + ")";
            }else{
                return "NormInv()";
            }
        }else{
            if(_hasSeed){
                return "NormInv(" + _Rand.ToString() + ", " + _Mean.ToString() + ", " + _StDev.ToString() + ")"; 
            }else{
                return "NormInv(" + _Mean.ToString() + ", " + _StDev.ToString() +")"; 
            }
        }  
    }
    @Override
    public boolean ContainsVariable() {return true;}
}
