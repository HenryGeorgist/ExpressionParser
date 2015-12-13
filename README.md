# ExpressionParser
This expression parser was something I decided to write a few years back to help me learn how to program (the original was in vb.net).  Ultimately it became a part of a larger project. I decided to convert it to Java so that other people could have access. The objective is to have something simmilar to excel syntax so that expressions can be simpler to type for users.  These expressions can be used to evaluate new data, edit existing data, or search through databases.  Another application would be for combining data that occurs at the same time (in time series data) or in the same location (gridded data). 

## Parser
ExpressionParser has one major entry point the Parser.  The parser is composed of a scanner and a current token. Parser.Parse is essentially a recursive parser that constructs a tree of ParseTreeNodes. Parser.Parse produces the ParseTreeNode tree which can then be evaluated.

The tree will have an output type, and all of the ParseTreeNodes that create the tree will also have types. The ParseTreeNodes do substatntial checking for validity of type comparison. Currently improper syntax will lead to observable notifications which contain statements like "cannot add type boolean and type string with operator +".  

Currently there are syntax errors that are contained in a shared errors array on the parseTreenode (this requires initialization through a static method prior to parsing), and scanner errors that occur due to inappropriate key word use or missing syntax issues.

## Scanner
The scanner is not expected to be an entry point to external users, but currently it is public, so users could get to it i suppose.  The scanner scans through the input byte stream one char at a time and produces tokens for the parser.  Each token currently knows its line location (hardly supported) and its start position in the entire input stream. 

Eventually I will add an additional argument to the token, a path to a help file.  This help file will be a path to an HTML file that can assist an end user in developing the proper syntax for the current token. I have a few hurdles to overcome to get to this point. I have a methodology which functions in .net, and I need to determine if the method will work adequately in java.

## ParseTreeNodes
The abstract class ParseTreeNode provides the required functionalities for any parse tree node. Certain functions (addition subtraction etc.) have caused me to create an intermediate parent BinaryExprNode, which handles the ToString() method and a few others. I also try to store the various functions logically, usually by the process rather than the outcome. For instance, contains is under strings rather than booleans.  Again, this is technically all something most programmers will not need to utilize directly, since the primary entry point is anticiapted to be the Parser.
