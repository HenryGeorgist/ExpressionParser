/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes;

import java.util.ArrayList;

/**
 *
 * @author Will_and_Sara
 */
public abstract class ParseTreeNode{
    protected TypeEnum _Type;
    protected static ArrayList<String> _Errors;
    public abstract Tokens Token();
    public abstract ParseTreeNodeResult Evaluate();
    public abstract void Update(Object[] row);
    public abstract String ToString();
    public abstract boolean ContainsVariable();
    public static void InitializeParseTreeNode(){
        _Errors = new ArrayList<>();
    }
    public TypeEnum Type(){return _Type;}
    public boolean ContainsErrors(){return !_Errors.isEmpty();}
    public ArrayList<String> GetErrors(){
        return _Errors;
    }
}
