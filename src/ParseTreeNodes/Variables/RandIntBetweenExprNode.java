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
public class RandIntBetweenExprNode extends ParseTreeNodes.ParseTreeNode implements ParseTreeNodes.IDisplayToTreeNode{
    private ParseTreeNodes.ParseTreeNode _Seed;
    private java.util.Random _Rand;
    private ParseTreeNodes.ParseTreeNode _Min;
    private ParseTreeNodes.ParseTreeNode _Max;
    public RandIntBetweenExprNode(ParseTreeNodes.ParseTreeNode Min, ParseTreeNodes.ParseTreeNode Max){
        super();
        if(Min == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The Min argument of RandIntBetween was not defined.");
        }else if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(Min.Type())){
            _Min = Min;
        }else{
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The Min argument of RandIntBetween was not a whole number.");
        }
        if(Max == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The Max argument of RandIntBetween was not defined.");
        }else if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(Max.Type())){
            _Max = Max;
        }else{
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The Max argument of RandIntBetween was not a whole number.");
        }
        _Type = ParseTreeNodes.TypeEnum.LONG;
        _Rand = new java.util.Random();
    }
    public RandIntBetweenExprNode(ParseTreeNodes.ParseTreeNode Min, ParseTreeNodes.ParseTreeNode Max, ParseTreeNodes.ParseTreeNode seed){
        super();
        if(Min == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The Min argument of RandIntBetween was not defined.");
        }else if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(Min.Type())){
            _Min = Min;
        }else{
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The Min argument of RandIntBetween was not a whole number.");
        }
        if(Max == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The Max argument of RandIntBetween was not defined.");
        }else if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(Max.Type())){
            _Max = Max;
        }else{
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The Max argument of RandIntBetween was not a whole number.");
        }
        if(seed == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The seed argument in the RandIntBetween expression was not defined.");
        }else if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(seed.Type())){
            _Seed = seed;
            _Rand = new java.util.Random(Long.parseLong(_Seed.Evaluate().Result().toString()));
            _Type = ParseTreeNodes.TypeEnum.LONG;
        }else{
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The seed argument in the RandIntBetween expression did not produce a whole number.");
        }
    }
    @Override
    public Tokens Token() {
        return ParseTreeNodes.Tokens.RANDINTBETWEEN;
    }
    @Override
    public ParseTreeNodeResult Evaluate() {
        double r = _Rand.nextInt();
        int mi = Integer.parseInt(_Min.Evaluate().Result().toString());
        int ma = Integer.parseInt(_Max.Evaluate().Result().toString());
        return new ParseTreeNodes.ParseTreeNodeResult(mi + (int)java.lang.Math.floor(r*(ma-mi)),_Type);
    }

    @Override
    public void Update(Object[] row) {
        if(_Seed!=null){
            if(_Seed.ContainsVariable()){
                _Seed.Update(row);
                _Rand = new java.util.Random(Long.parseLong(_Seed.Evaluate().Result().toString()));
            }
        }
        if(_Min!=null){
            if(_Min.ContainsVariable()){
                _Min.Update(row);
            }
        }
        if(_Max!=null){
            if(_Max.ContainsVariable()){
                _Max.Update(row);
            }
        }
    }

    @Override
    public String ToString() {
        if(_Seed == null){
            if(_Min == null){
                if(_Max == null){
                    return "RANDINTBETWEEN(,,)"; 
                }else{
                    return "RANDINTBETWEN(,," + _Max.ToString() + ")";
                }
            }else{
                if(_Max == null){
                    return "RANDINTBETWEN(," + _Min.ToString() + ",)";
                }else{
                    return "RANDINTBETWEN(," + _Min.ToString() + "," + _Max.ToString() + ")";
                }
            } 
        }else{
            if(_Min == null){
                if(_Max == null){
                    return "RANDINTBETWEEN(" + _Seed.ToString() + ",,)"; 
                }else{
                    return "RANDINTBETWEN(" + _Seed.ToString() + ",," + _Max.ToString() + ")";
                }
            }else{
                if(_Max == null){
                    return "RANDINTBETWEN(" + _Seed.ToString() + "," + _Min.ToString() + ",)";
                }else{
                    return "RANDINTBETWEN(" + _Seed.ToString() + "," + _Min.ToString() + "," + _Max.ToString() + ")";
                }
            } 
        } 
    }

    @Override
    public boolean ContainsVariable() {
        return _Seed.ContainsVariable() || _Min.ContainsVariable() || _Max.ContainsVariable();
    }
    @Override
    public ArrayList<String> GetComputeErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_ComputeErrors);
        Errors.addAll(_Seed.GetComputeErrors());
        Errors.addAll(_Min.GetComputeErrors());
        Errors.addAll(_Max.GetComputeErrors());
        return Errors;
    }
    @Override
    public ArrayList<String> GetSyntaxErrors() {
        ArrayList<String> Errors = new ArrayList<>();
        Errors.addAll(_SyntaxErrors);
        Errors.addAll(_Seed.GetSyntaxErrors());
        Errors.addAll(_Min.GetSyntaxErrors());
        Errors.addAll(_Max.GetSyntaxErrors());
        return Errors;    
    }
    @Override
    public String DisplayName() {return "RandIntBetween";}
    @Override
    public String HelpFilePath() {return "www.yahoo.com";}
    @Override
    public String Syntax() {return "RandIntBetween(,,)";}
}
