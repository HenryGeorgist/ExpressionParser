/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

/**
 *
 * @author Will_and_Sara
 */
public class ExampleParser {
    public static void main(String args[]){
        Parser P = new Parser();
        ParseTreeNodes.ParseTreeNode result;
        result = P.Parse("abcd + 2");
        if(result.ContainsSyntaxErrors()){
            System.out.println("ParseTreeNode Syntax Errors: ");
            System.out.println(result.GetSyntaxErrors().get(0));
        }
        result = P.Parse("1+2*3");
        if(!result.ContainsSyntaxErrors()){
            System.out.println(result.ToString() + " = " + result.Evaluate().Result().toString());
        }
    }
}
