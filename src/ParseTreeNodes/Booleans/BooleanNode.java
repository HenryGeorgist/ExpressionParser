/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Booleans;

/**
 *
 * @author Will_and_Sara
 */
public class BooleanNode extends ParseTreeNodes.ParseTreeNode {
    private String _s;
    public BooleanNode(String s){
        _s = s;//must be true True false or False unless i set the environment variables to be different
        _Type = ParseTreeNodes.TypeEnum.BOOLEAN;
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.BOOLLIT;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
        return new ParseTreeNodes.ParseTreeNodeResult(Boolean.parseBoolean(_s), _Type);
    }
    @Override
    public void Update(Object[] row) {
        //do nothing, this is not a variable
    }
    @Override
    public String ToString() {
        return _s;
    }
    @Override
    public void SetColumnNumbers(String[] ColumnNames) {
        //do nothing, this is a terminal branch in the tree.
    }
    @Override
    public boolean ContainsVariable() {
        return false;
    }
}
