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
public class Variable extends ParseTreeNodes.ParseTreeNode{
    private Object _CurrentValue;
    private int _ColumnIndex;
    private final String _ColumnName;
    public Variable(String ColumnName, ParseTreeNodes.TypeEnum Type, int ColumnIndex){
        _ColumnName = ColumnName;
        _ColumnIndex = ColumnIndex;
        _Type = Type;
    }
    @Override
    public Tokens Token() {
        return ParseTreeNodes.Tokens.COLUMN;
    }
    @Override
    public ParseTreeNodeResult Evaluate() {
        return new ParseTreeNodes.ParseTreeNodeResult(_CurrentValue,_Type);
    }
    @Override
    public void Update(Object[] row) {
        _CurrentValue = row[_ColumnIndex];
    }
    @Override
    public String ToString() {
        return "[" + _ColumnName + "]";
    }
    @Override
    public void SetColumnNumbers(String[] ColumnNames) {
        for(int i = 0;i<ColumnNames.length;i++){
            if(ColumnNames[i].equals(_ColumnName)){_ColumnIndex = i;}
        }
        //should probably check that the value was set, what if it wasnt?
    }
    @Override
    public boolean ContainsVariable() {
        return true;
    }
    
}
