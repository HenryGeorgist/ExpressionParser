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
public class Parser {
   private Scanner.Scanner _Scanner;
   private ParseTreeNodes.Token _Tok;
   //private String[] _VariableNames;
   //private Type[] _VariableTypes;
   public Parser(Scanner.Scanner scanner){
       _Scanner = scanner;
   }
   public Parser(){
       
   }
   private void Scan(){
       _Tok = _Scanner.Scan();
   }
   private ParseTreeNodes.ParseTreeNode NumFactor(){
       //if the result of parseatreenode is numerical return, else return an error.
       ParseTreeNodes.ParseTreeNode ret = null;
       //Scan();
       switch(_Tok.GetToken()){
           case NUMLIT:
               ret = new ParseTreeNodes.Numerics.NumNode(Double.parseDouble(_Tok.GetString()));
               Scan();
               return ret;
           case NUMLITINT:
               ret =new ParseTreeNodes.Numerics.NumIntNode(Integer.parseInt(_Tok.GetString()));
               Scan();
               return ret;
           case LPAREN:
               Scan();
               ret = ParentheticalStatement(ret);
               //Scan();
               return ret;
           default:
           return ret;//need to create an error node
       }

   }
   private ParseTreeNodes.ParseTreeNode NumTerm(){
       return NumTerm(NumFactor());
   }
   private ParseTreeNodes.ParseTreeNode NumTerm(ParseTreeNodes.ParseTreeNode lefthandSide){
       while(ParseTreeNodes.Tokens.TERM.contains(_Tok.GetToken())){
           if(_Tok.GetToken()==ParseTreeNodes.Tokens.TIMES){
               Scan();
               lefthandSide = new ParseTreeNodes.Numerics.MultiplyExprNode(lefthandSide, NumFactor());
           }else if(_Tok.GetToken()==ParseTreeNodes.Tokens.DIVIDE){
               Scan();
               lefthandSide = new ParseTreeNodes.Numerics.DivideExprNode(lefthandSide, NumFactor());
           }else if(_Tok.GetToken()==ParseTreeNodes.Tokens.EXPONENT){
               Scan();
               lefthandSide = new ParseTreeNodes.Numerics.ExponentExprNode(lefthandSide, NumFactor());
           }
       }
       return lefthandSide;
   }
   private ParseTreeNodes.ParseTreeNode NumExpr(ParseTreeNodes.ParseTreeNode lefthandSide){
       while(ParseTreeNodes.Tokens.EXPR.contains(_Tok.GetToken())){
           if(_Tok.GetToken()==ParseTreeNodes.Tokens.MINUS){
               Scan();
               if(lefthandSide==null){
                   lefthandSide = new ParseTreeNodes.Numerics.NegationNode(NumFactor());
               }else{
                  lefthandSide = new ParseTreeNodes.Numerics.MinusExprNode(lefthandSide, NumTerm()); 
               }
           }else{
               Scan();
               lefthandSide = new ParseTreeNodes.Numerics.PlusExprNode(lefthandSide, NumTerm());
           }
       }
       return lefthandSide;
   }
   private ParseTreeNodes.ParseTreeNode ParentheticalStatement(ParseTreeNodes.ParseTreeNode lefthandSide){
       //ParseTreeNodes.ParseTreeNode Ret = null;
       //Scan();
       while(_Tok.GetToken()!= ParseTreeNodes.Tokens.RPAREN){
           lefthandSide = ParseATreeNode(lefthandSide);//somehow i end up in an infinate do loop.
           //Scan();
       }
       Scan();
       return lefthandSide;
   }
   private ParseTreeNodes.ParseTreeNode FactorAnd(){
       ParseTreeNodes.ParseTreeNode lefthandSide = Parse();
       while(_Tok.GetToken()!= ParseTreeNodes.Tokens.RPAREN){
           if(_Tok.GetToken()==ParseTreeNodes.Tokens.COMMA){
               lefthandSide = new ParseTreeNodes.Booleans.AndExprNode(lefthandSide, Parse());
           }
       }
       return lefthandSide; 
   }
   private ParseTreeNodes.ParseTreeNode FactorOr(){
       ParseTreeNodes.ParseTreeNode lefthandSide = Parse();
       while(_Tok.GetToken()!= ParseTreeNodes.Tokens.RPAREN){
           if(_Tok.GetToken()==ParseTreeNodes.Tokens.COMMA){
               lefthandSide = new ParseTreeNodes.Booleans.OrExprNode(lefthandSide, Parse());
           }
       }
       return lefthandSide; 
   }
   private ParseTreeNodes.ParseTreeNode FactorIf(){
       ParseTreeNodes.Booleans.IfExprNode IfNode = new ParseTreeNodes.Booleans.IfExprNode(Parse());
       //comma
       IfNode.SetThen(Parse());
       //comma
       IfNode.SetElse(Parse());
       //Right Parenthisis
       Scan();
       return IfNode;
   }
   private ParseTreeNodes.ParseTreeNode FactorContains(){
       return new ParseTreeNodes.Strings.ContainsExprNode(Parse(), Parse());
   }
   private ParseTreeNodes.ParseTreeNode ParseATreeNode(ParseTreeNodes.ParseTreeNode lefthandSide){
       boolean exitloop = false;
       switch(_Tok.GetToken()){
           case LPAREN:
               Scan();
               lefthandSide = ParentheticalStatement(lefthandSide);
               break;
           case RPAREN:
               exitloop = true;
//           case LBRACKET:
//               break;
//           case RBRACKET:
//               break;
//           case CLBRACKET:
//               break;
//           case CRBRACKET:
//               break;
//           case COLUMN:
//               break;
           case PLUS:
               lefthandSide = NumExpr(lefthandSide);
               break;
           case MINUS:
               lefthandSide = NumExpr(lefthandSide);
               break;
           case TIMES:
               lefthandSide = NumTerm(lefthandSide);
               break;
           case DIVIDE:
               lefthandSide = NumTerm(lefthandSide);
               break;
           case EXPONENT:
               lefthandSide = NumTerm(lefthandSide);
               break;
           case EQ:
               lefthandSide = new ParseTreeNodes.Booleans.EqualsExprNode(lefthandSide, Parse());
               break;
           case NEQ:
               lefthandSide = new ParseTreeNodes.Booleans.NotEqualsExprNode(lefthandSide, Parse());
               break;
           case LT:
               lefthandSide = new ParseTreeNodes.Booleans.LessThanExprNode(lefthandSide, Parse());
               break;
           case GT:
               lefthandSide = new ParseTreeNodes.Booleans.GreaterThanExprNode(lefthandSide, Parse());
               break;
           case LTE:
               lefthandSide = new ParseTreeNodes.Booleans.LessThanOrEqualExprNode(lefthandSide, Parse());
               break;
           case GTE:
               lefthandSide = new ParseTreeNodes.Booleans.GreaterThanOrEqualExprNode(lefthandSide, Parse());
               break;
           case EOF:
               exitloop = true;
               break;
           case ERR:
               exitloop = true;
               break;
//           case ID:
//               break;
           case IF:
               Scan();//if token
               lefthandSide = FactorIf();
               break;
           case AND:
               Scan();//and token
               lefthandSide = FactorAnd();
               break;
           case OR:
               Scan();
               lefthandSide = FactorOr();
               break;
           case NUMLIT:
               lefthandSide = new ParseTreeNodes.Numerics.NumNode(Double.parseDouble(_Tok.GetString()));
               Scan();
               break;
           case NUMLITINT:
               lefthandSide = new ParseTreeNodes.Numerics.NumIntNode(Integer.parseInt(_Tok.GetString()));
               Scan();
               break;
//           case INDEX:
//               break;
           case CONTAINS:
               Scan();//Contains token
               lefthandSide = FactorContains();
               Scan();//Right Parenthesis.
               break;
//           case OFFSET:
//               break;
           case STRINGLIT:
               lefthandSide = new ParseTreeNodes.Strings.StringNode(_Tok.GetString());
               Scan();
               break;
           case BOOLLIT:
               lefthandSide = new ParseTreeNodes.Booleans.BooleanNode(_Tok.GetString());
               Scan();
               break;
           case COMMA:
               exitloop = true;
               break;
           case ANDPERSTAND:
               //Scan();
//               do while(_Tok.GetToken()== ParseTreeNodes.Tokens.ANDPERSTAND){
//                   Scan();
//                   if(_Tok.GetToken()==ParseTreeNodes.Tokens.STRINGLIT || _Tok.GetToken()==ParseTreeNodes.Tokens.NUMLIT )
                   lefthandSide = new ParseTreeNodes.Strings.ConcatenateExprNode(lefthandSide, Parse());
//               }
               
               break;
           case RIGHT:
               Scan();
               lefthandSide = new ParseTreeNodes.Strings.RightExprNode(Parse(),Parse());
               Scan();
               break;
           case LEFT:
               Scan();
               lefthandSide = new ParseTreeNodes.Strings.LeftExprNode(Parse(),Parse());
               Scan();
               break;
//           case LEN:
//               break;
//           case ROUND:
//               break;
//           case ROUNDUP:
//               break;
//           case ROUNDDOWN:
//               break;
//           case RAND:
//               break;
//           case RANDINT:
//               break;
//           case RANDBETWEEN:
//               break;
//           case RANDINTBETWEEN:
//               break;
//           case NORMINV:
//               break;
//           case TRIINV:
//               break;
//           case SUBSTRING:
//               break;
//           case INSTRING:
//               break;
//           case CONVERTTOINT:
//               break;
//           case CONVERTTODOUBLE:
//               break;
//           case CONVERTTOSINGLE:
//               break;
//           case CONVERTTOSTRING:
//               break;
//           case CONVERTTOBOOLEAN:
//               break;
//           case CONVERTTOSHORT:
//               break;
//           case CONVERTTOBYTE:
//               break;
           default:
               throw new AssertionError(_Tok.GetToken().name());
               
       }
       if(exitloop){
           return lefthandSide;
       }else{
           return ParseATreeNode(lefthandSide);
       }
       
   }
   public ParseTreeNodes.ParseTreeNode Parse(){
       Scan();
       ParseTreeNodes.ParseTreeNode Result = ParseATreeNode(null);
       return Result;
   }
      public ParseTreeNodes.ParseTreeNode Parse(String Expression){
       java.io.InputStream is = new java.io.ByteArrayInputStream(Expression.getBytes());
       _Scanner = new Scanner.Scanner(is);
       Scan();
       ParseTreeNodes.ParseTreeNode Result = ParseATreeNode(null);
       return Result;
   }
}
