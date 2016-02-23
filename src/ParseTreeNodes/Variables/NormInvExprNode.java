/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Variables;

import ParseTreeNodes.ParseTreeNodeResult;
import ParseTreeNodes.Tokens;
import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public class NormInvExprNode extends ParseTreeNodes.ParseTreeNode implements ParseTreeNodes.IDisplayToTreeNode{
    protected ParseTreeNodes.ParseTreeNode _Mean;
    private ParseTreeNodes.ParseTreeNode _StDev;
    private ParseTreeNodes.ParseTreeNode _Rand;
    private boolean _isStandard = false;
    private boolean _hasRandom = false;
    public NormInvExprNode(){
        //for reflection
    }
    public NormInvExprNode(ParseTreeNodes.ParseTreeNode mean, ParseTreeNodes.ParseTreeNode stdev){
        super();
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
    public NormInvExprNode(ParseTreeNodes.ParseTreeNode rand){
        super();
        _Mean = new ParseTreeNodes.Numerics.NumNode(0.0);
        _StDev = new ParseTreeNodes.Numerics.NumNode(1.0);
        if(rand == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The random argument of the standard normal inverse CDF function was not provided.");
        }else{
            if(ParseTreeNodes.TypeEnum.DECIMAL.contains(rand.Type())){
                _isStandard = true;
                _hasRandom = true;
                _Rand = rand;
                _Type = ParseTreeNodes.TypeEnum.DOUBLE;
            }else{
                _Type = ParseTreeNodes.TypeEnum.ERR;
                _SyntaxErrors.add("The random argument in the standard normal inverse CDF function did not produce a decimal number.");
            }
        }
    }
    public NormInvExprNode(ParseTreeNodes.ParseTreeNode rand, ParseTreeNodes.ParseTreeNode mean, ParseTreeNodes.ParseTreeNode stdev){
        //check for null nodes and bad input types...
        super();
        _hasRandom = true;
        _Mean = mean;
        _StDev = stdev;
        _Rand = rand;
    }
    @Override
    public Tokens Token() {return ParseTreeNodes.Tokens.NORMINV;}
    @Override
    public ParseTreeNodeResult Evaluate() {
        double m = Double.parseDouble(_Mean.Evaluate().Result().toString());
        double s = Double.parseDouble(_StDev.Evaluate().Result().toString());
        double r = Double.parseDouble(_Rand.Evaluate().Result().toString());
        Distributions.MethodOfMoments.Normal N = new Distributions.MethodOfMoments.Normal(m,s);
        return new ParseTreeNodes.ParseTreeNodeResult(N.GetInvCDF(r),_Type);
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
            if(_hasRandom){
                return "NormInv(" + _Rand.ToString() + ")";
            }else{
                return "NormInv()";
            }
        }else{
            if(_hasRandom){
                return "NormInv(" + _Rand.ToString() + ", " + _Mean.ToString() + ", " + _StDev.ToString() + ")"; 
            }else{
                return "NormInv(" + _Mean.ToString() + ", " + _StDev.ToString() +")"; 
            }
        }  
    }
    @Override
    public boolean ContainsVariable() {return true;}
    @Override
    public ArrayList<String> GetComputeErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_ComputeErrors);
        Errors.addAll(_Rand.GetComputeErrors());
        Errors.addAll(_Mean.GetComputeErrors());
        Errors.addAll(_StDev.GetComputeErrors());
        return Errors;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_SyntaxErrors);
        Errors.addAll(_Rand.GetSyntaxErrors());
        Errors.addAll(_Mean.GetSyntaxErrors());
        Errors.addAll(_StDev.GetSyntaxErrors());
        return Errors;
    }
    @Override
    public String DisplayName() {return "NormInv";}
    @Override
    public String HelpFilePath() {return "www.netflix.com";}
    @Override
    public String Syntax() {return "NormInv(,,)";}
}
