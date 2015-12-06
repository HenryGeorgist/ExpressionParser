/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scanner;

/**
 *
 * @author Will_and_Sara
 */
public class NumberParseResult {
    public String Str;
    public boolean HasDecimal = false;
    public boolean IsNumerical = true;
    public NumberParseResult(String s, boolean HasD, boolean IsNumber){
        Str = s;
        HasDecimal = HasD;
        IsNumerical = IsNumber;
    }
}
