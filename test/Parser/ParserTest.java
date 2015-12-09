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
        assertEquals("12", _P.Parse("(3+3)*2").Evaluate().Result().toString());
        assertEquals("15", _P.Parse("3*(3+2)").Evaluate().Result().toString());    
        assertEquals("9", _P.Parse("3+3*2").Evaluate().Result().toString());
        assertEquals("11", _P.Parse("3*3+2").Evaluate().Result().toString());
        assertEquals("false", _P.Parse("3>11").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("3<11").Evaluate().Result().toString());
        assertEquals("false", _P.Parse("3=11").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("3!=11").Evaluate().Result().toString());
        assertEquals("false", _P.Parse("3>=11").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("3<=11").Evaluate().Result().toString());
        assertEquals("false", _P.Parse("fish = cow").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("fish = fish").Evaluate().Result().toString());
        assertEquals("false", _P.Parse("fi & sh = cow").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("fi & sh = fish").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("fish = fi & sh").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("fish!=cow").Evaluate().Result().toString());
        assertEquals("false", _P.Parse("fish!=fish").Evaluate().Result().toString());
        assertEquals("false", _P.Parse("and(1!=1,3<=11,5=6)").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("or(1!=1,3<=11,5=6)").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("if(1=1,3<=11,fish=cow) = true").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("if(1!=1,3<=11,fish=cow) = false").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("if(1=1,123,456)=123").Evaluate().Result().toString());
        assertEquals("true", _P.Parse("contains(asdf,a)").Evaluate().Result().toString());
        assertEquals("false", _P.Parse("contains(asdf,b)").Evaluate().Result().toString());
        assertEquals("f", _P.Parse("Right(asdf,1)").Evaluate().Result().toString());
        assertEquals("a", _P.Parse("left(asdf,1)").Evaluate().Result().toString());
        assertEquals("sd", _P.Parse("substring(asdf,1,2)").Evaluate().Result().toString());
        assertEquals("0.6465821602909256", _P.Parse("Rand(1234)").Evaluate().Result().toString());
        assertEquals("-1517918040", _P.Parse("RandINT(1234)").Evaluate().Result().toString());
    }
    
}
