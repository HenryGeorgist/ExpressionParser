/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Will_and_Sara
 */
public class ParserTest {
    private Parser _P;
    public ParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        _P = new Parser();
    }
    
    @After
    public void tearDown() {
    }
    /**
     * Test of Parse method, of class Parser.
     */
    @Test
    public void testParse_String() {
        System.out.println("Parse");
        assertEquals(true, _P.Parse("1+abcd").ContainsSyntaxErrors());
        assertEquals("Cannot Add LONG and STRING, operation: 1 + abcd is invalid.", _P.Parse("1+abcd").GetSyntaxErrors().get(0));
        assertEquals(0, _P.Parse("-1+1").Evaluate().Result());
        assertEquals(-9.0, _P.Parse("- 3^2)").Evaluate().Result());
        assertEquals(12, _P.Parse("(3 +3)* 2").Evaluate().Result());
        assertEquals(15, _P.Parse("3*(3 + 2)").Evaluate().Result());    
        assertEquals(9, _P.Parse("3+3*2").Evaluate().Result());
        assertEquals(11, _P.Parse("3*3+ 2").Evaluate().Result());
        assertEquals(false, _P.Parse("3>11").Evaluate().Result());
        assertEquals(true, _P.Parse("3<11").Evaluate().Result());
        assertEquals(false, _P.Parse("3=11").Evaluate().Result());
        assertEquals(true, _P.Parse("3!=11").Evaluate().Result());
        assertEquals(false, _P.Parse("3>=11").Evaluate().Result());
        assertEquals(true, _P.Parse("3<=11").Evaluate().Result());
        assertEquals(false, _P.Parse("fish = cow").Evaluate().Result());
        assertEquals(true, _P.Parse("'fi sh' = \"fi sh\"").Evaluate().Result());
        assertEquals(false, _P.Parse("fi & sh = cow").Evaluate().Result());
        assertEquals(true, _P.Parse("fi & sh = fish").Evaluate().Result());
        assertEquals(true, _P.Parse("fish = fi & sh").Evaluate().Result());
        assertEquals(true, _P.Parse("fish!=cow").Evaluate().Result());
        assertEquals(false, _P.Parse("fish!= fish").Evaluate().Result());
        assertEquals(false, _P.Parse("and(1 !=1,3<= 11,5=6)").Evaluate().Result());
        assertEquals(true, _P.Parse("or(1!=1,3 <=11,5=6)").Evaluate().Result());
        assertEquals(true, _P.Parse("if(1=1,3 <= 11,fish=cow) = true").Evaluate().Result());
        assertEquals(true, _P.Parse("if(1!=1,3<=11,fish=cow) = false").Evaluate().Result());
        assertEquals(true, _P.Parse("if(1=1,123,456)= 123").Evaluate().Result());
        assertEquals(true, _P.Parse("contains(asdf,a)").Evaluate().Result());
        assertEquals(false, _P.Parse("contains(asdf,b)").Evaluate().Result());
        assertEquals("f", _P.Parse("Right(asdf,1)").Evaluate().Result());
        assertEquals("a", _P.Parse("left(asdf,1)").Evaluate().Result());
        assertEquals("sd", _P.Parse("substring(asdf,1,2)").Evaluate().Result());
        assertEquals(0.6465821602909256, _P.Parse("Rand(1234)").Evaluate().Result());
        assertEquals(-1517918040, _P.Parse("RandINT(1234)").Evaluate().Result());
        ParseTreeNodes.TypeEnum[] types;
        types = new ParseTreeNodes.TypeEnum[2];
        types[0] = ParseTreeNodes.TypeEnum.STRING;
        types[1] = ParseTreeNodes.TypeEnum.DOUBLE;
        String[] Names;
        Names = new String[2];
        Names[0] = "A";
        Names[1] = "B";
        Object[] values;
        values = new Object[2];
        values[0] = "SomeString";
        values[1] = 1.5;
        _P.SetColumnNames(Names);
        _P.SetTypes(types);
        ParseTreeNodes.ParseTreeNode node = _P.Parse("[A] & morestring");
        node.Update(values);
        assertEquals("SomeStringmorestring", node.Evaluate().Result());
        values[0] = "OtherString";
        node.Update(values);
        assertEquals("OtherStringmorestring", node.Evaluate().Result());
        node = _P.Parse("[B]+.5");
        node.Update(values);
        assertEquals(2.0, node.Evaluate().Result());
        values[1] = .5;
        node.Update(values);
        assertEquals(1.0, node.Evaluate().Result());
    }
    
}
