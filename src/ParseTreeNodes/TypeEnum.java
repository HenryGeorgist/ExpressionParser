/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParseTreeNodes;
import java.util.EnumSet;
/**
 *
 * @author Will_and_Sara
 */
public enum TypeEnum {
    ERR,
    DOUBLE,
    SINGLE,
    SHORT,
    LONG,
    BYTE,
    STRING,
    BOOLEAN,
    UNDECLARED;
    public static final EnumSet<TypeEnum> WHOLENUMBER = EnumSet.of(SHORT,LONG,BYTE);
    public static final EnumSet<TypeEnum> DECIMAL = EnumSet.of(DOUBLE,SINGLE);
    public static final EnumSet<TypeEnum> NUMERICAL = EnumSet.of(DOUBLE,SINGLE,SHORT,BYTE,LONG);
    public static final EnumSet<TypeEnum> VALID = EnumSet.of(DOUBLE,SINGLE,SHORT,LONG,BYTE,STRING,BOOLEAN,UNDECLARED);
}
