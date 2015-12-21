/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes.Strings;

import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public class StringNode  extends ParseTreeNodes.ParseTreeNode {
    private String _s;
    public StringNode(String s){
        super();
        _s = s;
        _Type = ParseTreeNodes.TypeEnum.STRING;
    }
    @Override
    public ParseTreeNodes.Tokens Token() {
        return ParseTreeNodes.Tokens.STRINGLIT;
    }
    @Override
    public ParseTreeNodes.ParseTreeNodeResult Evaluate() {
        return new ParseTreeNodes.ParseTreeNodeResult(_s, _Type);
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
    public boolean ContainsVariable() {
        return false;
    }

    @Override
    public ArrayList<String> GetComputeErrors() {
        return _ComputeErrors;
    }

    @Override
    public ArrayList<String> GetSyntaxErrors() {
        return _SyntaxErrors;
    }
}
