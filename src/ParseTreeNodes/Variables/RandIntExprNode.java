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
public class RandIntExprNode extends ParseTreeNodes.ParseTreeNode{
    private ParseTreeNodes.ParseTreeNode _Seed;
    private java.util.Random _Rand;
    public RandIntExprNode(){
        _Rand = new java.util.Random();
    }
    public RandIntExprNode(ParseTreeNodes.ParseTreeNode seed){
        if(seed == null){
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The seed argument in the RandInt expression was not defined.");
        }else if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(seed.Type())){
            _Seed = seed;
            _Rand = new java.util.Random(Long.parseLong(_Seed.Evaluate().Result().toString()));
            _Type = ParseTreeNodes.TypeEnum.LONG;
        }else{
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("The seed argument in the RandInt expression did not produce a whole number.");
        }
    }
    @Override
    public ParseTreeNodes.Tokens Token() {return ParseTreeNodes.Tokens.RANDINT;}
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
        return new ParseTreeNodes.ParseTreeNodeResult(_Rand.nextInt(),_Type);
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
            return "RANDINT()"; 
        }else{
            return "RANDINT("+_Seed.ToString() + ")"; 
        }  
    }
    @Override
    public boolean ContainsVariable() {return true;}   
}
