/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Variables;

/**
 *
 * @author Will_and_Sara
 */
public class RandExprNode extends ParseTreeNodes.ParseTreeNode{
    private ParseTreeNodes.ParseTreeNode _Seed;
    private java.util.Random _Rand;
    public RandExprNode(){
        _Rand = new java.util.Random();
    }
    public RandExprNode(ParseTreeNodes.ParseTreeNode seed){
        if(seed == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The seed argument in the Rand expression was not defined.");
        }else if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(seed.Type())){
            _Seed = seed;
            _Rand = new java.util.Random(Long.parseLong(_Seed.Evaluate().Result().toString()));
            _Type = ParseTreeNodes.TypeEnum.DOUBLE;
        }else{
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The seed argument in the Rand expression does not produce a whole number.");
        }
    }
    @Override
    public ParseTreeNodes.Tokens Token() {return ParseTreeNodes.Tokens.RAND;}
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate(){
        return new ParseTreeNodes.ParseTreeNodeResult(_Rand.nextDouble(),_Type);
    }
    @Override
    public void Update(Object[] row) {
        if(_Seed!=null){
            if(_Seed.ContainsVariable()){
                _Seed.Update(row);
                _Rand = new java.util.Random(Long.parseLong(_Seed.Evaluate().Result().toString()));
            }
        }
    }
    @Override
    public String ToString() {
        if(_Seed == null){
            return "RAND()"; 
        }else{
            return "RAND("+_Seed.ToString() + ")"; 
        }  
    }
    @Override
    public boolean ContainsVariable() {return true;}
}
