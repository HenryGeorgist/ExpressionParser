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
    protected static int _RowOrCellNum;
    protected ArrayList<String> _SyntaxErrors;
    protected ArrayList<String> _ComputeErrors;
    public abstract Tokens Token();
    public abstract ParseTreeNodeResult Evaluate();
    public abstract void Update(Object[] row);
    public abstract String ToString();
    public abstract boolean ContainsVariable();
    public static void SetRoworCellNum(int RowOrCellNum){_RowOrCellNum = RowOrCellNum;}
    public ParseTreeNode(){
        _SyntaxErrors = new ArrayList<>();
        _ComputeErrors = new ArrayList<>();
    }
    public void ClearComputeErrors(){_ComputeErrors = new ArrayList<>();}
    public void ClearSyntaxErrors(){_SyntaxErrors = new ArrayList<>();}
    public abstract ArrayList<String> GetComputeErrors();
    public abstract ArrayList<String> GetSyntaxErrors();
    public TypeEnum Type(){return _Type;}
    public boolean ContainsSyntaxErrors(){
        ArrayList<String> test = GetSyntaxErrors();
        
        return !test.isEmpty();}
    public boolean ContainsComputeErrors(){
        return !GetComputeErrors().isEmpty();}
}
