/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes;

/**
 *
 * @author Will_and_Sara
 */
public class ParseTreeNodeResult {
    private Object _Result;
    private TypeEnum _Type;
    public ParseTreeNodeResult(Object R, TypeEnum T){
        _Result = R;
        _Type = T;
    }
    public Object Result(){return _Result;}
    public TypeEnum Type(){return _Type;}
}
