/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Numerics;

/**
 *
 * @author Will_and_Sara
 */
public class NegationNode extends ParseTreeNodes.ParseTreeNode {
    private ParseTreeNodes.ParseTreeNode _d;
    public NegationNode(ParseTreeNodes.ParseTreeNode d){
        _d = d;
        if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(_d.Type())){
          _Type = _d.Type();  
        }else{
            _Type = ParseTreeNodes.TypeEnum.ERR;
            _SyntaxErrors.add("A minus sign was followed by something that does not produce a numerical output");
        }
        
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.NUMLIT;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
        if(ParseTreeNodes.TypeEnum.WHOLENUMBER.contains(_Type)){
            return new ParseTreeNodes.ParseTreeNodeResult(-Integer.parseInt(_d.Evaluate().Result().toString()), _Type);
        }else{
            return new ParseTreeNodes.ParseTreeNodeResult(-Double.parseDouble(_d.Evaluate().Result().toString()), _Type);
        }   
    }
    @Override
    public void Update(Object[] row) {
        //do nothing, this is not a variable
    }
    @Override
    public String ToString() {
        return "-" + _d.ToString();
    }
    @Override
    public boolean ContainsVariable() {
        return false;
    }   
}
