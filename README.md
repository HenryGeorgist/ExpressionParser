# ExpressionParser
This expression parser is designed to have something similar to excel syntax so that expressions can be simpler to type for users than using vb c# or sequal logic statements.  These expressions can be used to evaluate new data, edit existing data, or search through databases.  Another application would be for combining data that occurs at the same time (in time series data) or in the same location (gridded data). 

## Parser
ExpressionParser has one major entry point the Parser.  The parser is composed of a scanner and a current token. Parser.Parse is essentially a recursive parser that constructs a tree of ParseTreeNodes. Parser.Parse produces the ParseTreeNode tree which can then be evaluated.

The tree will have an output type, and all of the ParseTreeNodes that create the tree will also have types. The ParseTreeNodes do substantial checking for validity of type comparison. Currently improper syntax will lead to observable notifications which contain statements like "cannot add type boolean and type string with operator +".  

Currently there are syntax errors that are contained in an errors array on the ParseTreenode (this requires initialization through the base class prior to construction of the expression), and scanner errors that occur due to inappropriate key word use or missing syntax issues.

## Scanner
The scanner scans through the input byte stream one char at a time and produces tokens for the parser.  Each token currently knows its line location and its start position in the entire input stream.

## ParseTreeNodes
The abstract class ParseTreeNode provides the required functionalities for any parse tree node. Certain functions (addition subtraction etc.) have caused me to create an intermediate parent BinaryExprNode, which handles the ToString() method and a few others. I also try to store the various functions logically, usually by the process rather than the outcome. For instance, contains is under strings rather than booleans.  Again, this is technically all something most programmers will not need to utilize directly, since the primary entry point is anticipated to be the Parser.

## Example Code
The following example shows how to utilize the Parser, it also shows how to determine if there are any syntax errors.

```java
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
```