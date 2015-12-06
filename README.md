# ExpressionParser
This expression parser was something I decided to write a few years back to help me learn how to program (the original was in vb.net).  Ultimately it became a part of a larger project. I decided to convert it to Java so that other people could have access. It is a work in progress, and will require a bit more code to really be useful. I am working on the base syntax right now, the objective is to have something simmilar to excel syntax. 

## Parser
ExpressionParser has one major entry point the Parser.  The parser is composed of a scanner and a current token. Parser.Parse is essentially a recursive parser that constructs a tree of ParseTreeNodes. Parser.Parse produces the ParseTreeNode tree which can then be evaluated.

The tree will have an output type, and all of the ParseTreeNodes that create the tree will also have types. The ParseTreeNodes do minimal checking for validity of type comparison. Currently improper syntax will lead to debug print statements like "cannot add type boolean and type string with operator +".  

Ultimately my parser and the individual parsetreenodes will need to raise error messages up, however, I have not formailized this methodology yet. In .net I used events, and I am trying to determine if that is the path I wish to pursue in this project.

## Scanner
The scanner is not intended to be an entry point to external users, but currently it is public, so users could get to it i suppose.  The scanner scans through the input byte stream one char at a time and produces tokens for the parser.  Each token currently knows its line location (hardly supported) and its start position in the entire input stream. 

Eventually I will add an additional argument to the token, a path to a help file.  This help file will be a path to an HTML file that can assist an end user in developing the proper syntax for the current token. I have a few hurdles to overcome to get to this point. I have a methodology which functions in .net, and I need to determine if the method will work adequately in java.

## ParseTreeNodes
The abstract class ParseTreeNode provides the required functionalities for any parse tree node. Certain functions (addition subtraction etc.) have caused me to create an intermediate parent BinaryExprNode, which handles the ToString() method and a few others. I also try to store the various functions logically, usually by the process rather than the outcome. For instance, contains is under strings rather than booleans.  Again, this is technically all something most programmers will not need to utilize directly, since the primary entry point is anticiapted to be the Parser.
