/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parser;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Will_and_Sara
 */
public class Parser extends Observable implements Observer{
   private Scanner.Scanner _Scanner;
   private ParseTreeNodes.Token _Tok;
   private String[] _VariableNames;
   private ParseTreeNodes.TypeEnum[] _VariableTypes;
   private ArrayList<String> _Errors;
//   public Parser(Scanner.Scanner scanner){
//       _Scanner = scanner;
//       _Scanner.addObserver(this);
//       _Errors = new ArrayList<>();
//   }
   public Parser(){
       _Errors = new ArrayList<>();
   }
   public void SetTypes(ParseTreeNodes.TypeEnum[] types){_VariableTypes = types;}
   public void SetColumnNames(String[] ColumnNames){_VariableNames = ColumnNames;}
   public ArrayList<String> GetErrors(){return _Errors;}
   public boolean ContainsErrors(){return !_Errors.isEmpty();}
   private void Scan(){
       _Tok = _Scanner.Scan();
       this.setChanged();
       this.notifyObservers(_Tok);
   }
   private ParseTreeNodes.ParseTreeNode NumFactor(){
       //if the result of parseatreenode is numerical return, else return an error.
       ParseTreeNodes.ParseTreeNode ret = null;
       //ret.addObserver(this);
       //Scan();
       switch(_Tok.GetToken()){
           case COLUMN:
               ret = FactorColumn();
               if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(ret.Type())){
                   return ret;
               }
               return new ParseTreeNodes.Strings.StringNode("NonNumerical Column");
               
           case NUMLIT:
               ret = new ParseTreeNodes.Numerics.NumNode(Double.parseDouble(_Tok.GetString()));
               Scan();
               return ret;
           case NUMLITINT:
               ret =new ParseTreeNodes.Numerics.NumIntNode(Integer.parseInt(_Tok.GetString()));
               Scan();
               return ret;
           case NORMINV:
                ret = FactorNormINV();
                Scan();
                return ret;
           case RAND:
               ret = FactorRand();
               return ret;
           case RANDINT:
               ret = FactorRandInt();
               return ret;
//           case RANDBETWEEN:
           case RANDINTBETWEEN:
               ret = FactorRandIntBetween();
               return ret;
           case IF:
               ret = FactorIf();
               if(ParseTreeNodes.TypeEnum.NUMERICAL.contains(ret.Type())){
                   ret = new ParseTreeNodes.Strings.StringNode("An If statement encountered in a larger numerical statement does not produce a numerical result");
               }
               return ret;    
           case LPAREN:
               Scan();
               ret = ParentheticalStatement(ret);
               //Scan();
               return ret;
           case LOG10:
               return FactorLog(true);
           case LN:
               return FactorLog(false);
           case EOF:
               return null;
           default:
               ret = new ParseTreeNodes.Strings.StringNode(_Tok.GetString());
               Scan();
               return ret;//need to create an error node
       }

   }
   private ParseTreeNodes.ParseTreeNode NumTerm(){
       return NumTerm(NumFactor());
   }
   private ParseTreeNodes.ParseTreeNode NumTerm(ParseTreeNodes.ParseTreeNode lefthandSide){
       while(ParseTreeNodes.Tokens.TERM.contains(_Tok.GetToken())){
           if(null!=_Tok.GetToken())switch (_Tok.GetToken()) {
               case TIMES:
                   Scan();
                   lefthandSide = new ParseTreeNodes.Numerics.MultiplyExprNode(lefthandSide, NumFactor());
                   break;
               case DIVIDE:
                   Scan();
                   lefthandSide = new ParseTreeNodes.Numerics.DivideExprNode(lefthandSide, NumFactor());
                   break;
               case EXPONENT:
                   Scan();
                   lefthandSide = new ParseTreeNodes.Numerics.ExponentExprNode(lefthandSide, NumFactor());
                   break;
               default:
                   break;
           }
       }
       return lefthandSide;
   }
   private ParseTreeNodes.ParseTreeNode NumExpr(ParseTreeNodes.ParseTreeNode lefthandSide){
       while(ParseTreeNodes.Tokens.EXPR.contains(_Tok.GetToken())){
           if(_Tok.GetToken()==ParseTreeNodes.Tokens.MINUS){
               Scan();
               if(lefthandSide==null){
                   lefthandSide = new ParseTreeNodes.Numerics.NegationNode(NumTerm());
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
       while(_Tok.GetToken()!= ParseTreeNodes.Tokens.RPAREN & _Tok.GetToken()!= ParseTreeNodes.Tokens.EOF & _Tok.GetToken()!=ParseTreeNodes.Tokens.ERR){
           lefthandSide = ParseATreeNode(lefthandSide);
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
   private ParseTreeNodes.ParseTreeNode FactorSubString(){
       Scan();
       return new ParseTreeNodes.Strings.SubStringExprNode(Parse(),Parse(),Parse());
   }
   private ParseTreeNodes.ParseTreeNode FactorContains(){
       return new ParseTreeNodes.Strings.ContainsExprNode(Parse(), Parse());
   }
   private ParseTreeNodes.ParseTreeNode FactorColumn(){
       ParseTreeNodes.ParseTreeNode ret= null;
       for(int i = 0; i<_VariableNames.length;i++){
           if(_VariableNames[i].equals(_Tok.GetString())){
            ret = new ParseTreeNodes.Variables.Variable(_Tok.GetString(),_VariableTypes[i],i);
            Scan();
            Scan();
            return ret;
           }
       }
       _Errors.add("Column Name " + _Tok.GetString() + " does not exist");
       return ret;
   }
   private ParseTreeNodes.ParseTreeNode StringFactor(){
       ParseTreeNodes.ParseTreeNode righthandSide;
       switch(_Tok.GetToken()){
           case COLUMN:
               righthandSide = FactorColumn();
               if(righthandSide.Type()!=ParseTreeNodes.TypeEnum.STRING){
                   _Errors.add("A column was referenced in a larger string expression and the type of the column was not string.");
                   righthandSide = null;
               }
               break;
           case LEFT:
               Scan();
               righthandSide = new ParseTreeNodes.Strings.LeftExprNode(Parse(),Parse());
               Scan();
               break;
           case RIGHT:
               Scan();
               righthandSide = new ParseTreeNodes.Strings.RightExprNode(Parse(),Parse());
               Scan();
               break;
           case IF:
               righthandSide = FactorIf();
               if(righthandSide.Type()!=ParseTreeNodes.TypeEnum.STRING){
                   _Errors.add("An if statement was found in a larger string expression, and the output type of the if was not a string.");
                   righthandSide = null;
               }
               break;
           case SUBSTRING:
               righthandSide = FactorSubString();
               Scan();
               break;
           case STRINGLIT:
               righthandSide = new ParseTreeNodes.Strings.StringNode(_Tok.GetString());
               Scan();
               break;
//         case CONVERTTOSTRING:
//             lefthandSide = new ParseTreeNodes.Strings.ConcatenateExprNode(lefthandSide, ParseATreeNode(null)); 
           default:
               _Errors.add("A string expression had the token " + _Tok.GetToken() + " and this token does not function in string expressions.");
               righthandSide = null;
               Scan();
       }
       return righthandSide;
   }
   /**
    * Creates a ParseTreeNode that is either Log10ExprNode or LnExprNode based on the boolean IsBase10
    * @param IsBase10
    * @return 
    */
   private ParseTreeNodes.ParseTreeNode FactorLog(boolean IsBase10){
       Scan();
       Scan();
       if(IsBase10){
           return new ParseTreeNodes.Numerics.Log10ExprNode(ParseATreeNode(null));
       }else{
           return new ParseTreeNodes.Numerics.LnExprNode(ParseATreeNode(null));
       }
   }
   private ParseTreeNodes.ParseTreeNode FactorRand(){
       Scan();
       Scan(); 
       if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
           Scan();
           return new ParseTreeNodes.Variables.RandExprNode();
       }else{
           return new ParseTreeNodes.Variables.RandExprNode(ParseATreeNode(null));
       }
   }
   private ParseTreeNodes.ParseTreeNode FactorRandInt(){
       Scan();
       Scan(); 
       if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
           Scan();
           return new ParseTreeNodes.Variables.RandIntExprNode();
       }else{
           return new ParseTreeNodes.Variables.RandIntExprNode(ParseATreeNode(null));
       }
   }
   private ParseTreeNodes.ParseTreeNode FactorRandIntBetween(){
       Scan();//RandIntBetween
       
       Scan();//LeftParen
       ParseTreeNodes.ParseTreeNode Min = null;
       ParseTreeNodes.ParseTreeNode Max = null;
       if(_Tok.GetToken() == ParseTreeNodes.Tokens.COMMA){
           //no min 
           Scan();//remove comma
           if(_Tok.GetToken() == ParseTreeNodes.Tokens.COMMA){
               //no max
               Scan();//remove comma
               if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
                  Scan();//RightParen
                  return new ParseTreeNodes.Variables.RandIntBetweenExprNode(Min,Max,null);
               }else{
                  //seed is available.
                  return new ParseTreeNodes.Variables.RandIntBetweenExprNode(Min,Max, ParseATreeNode(null));
               }
           }else if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
                  Scan();//RightParen
                  return new ParseTreeNodes.Variables.RandIntBetweenExprNode(Min,Max);
           }else{
               //max is available.
               Max = ParseATreeNode(null);
               if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
                  Scan();//RightParen
                  return new ParseTreeNodes.Variables.RandIntBetweenExprNode(Min,Max,null);
               }else{
                  //seed is available.
                  return new ParseTreeNodes.Variables.RandIntBetweenExprNode(Min,Max, ParseATreeNode(null));
               }
           }
       }else{
           //min
           Min = ParseATreeNode(null);
           if(_Tok.GetToken() == ParseTreeNodes.Tokens.COMMA){
               //no max
               Scan();//remove comma
               if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
                  Scan();//RightParen
                  return new ParseTreeNodes.Variables.RandIntBetweenExprNode(Min,Max,null);
               }else{
                  //seed is available.
                  return new ParseTreeNodes.Variables.RandIntBetweenExprNode(Min,Max, ParseATreeNode(null));
               }
           }else if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
                  Scan();//RightParen
                  return new ParseTreeNodes.Variables.RandIntBetweenExprNode(Min,Max);
           }else{
               //max is available.
               Max = ParseATreeNode(null);
               if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
                  Scan();//RightParen
                  return new ParseTreeNodes.Variables.RandIntBetweenExprNode(Min,Max,null);
               }else{
                  //seed is available.
                  return new ParseTreeNodes.Variables.RandIntBetweenExprNode(Min,Max, ParseATreeNode(null));
               }
           }
       }
   }
   private ParseTreeNodes.ParseTreeNode FactorNormINV(){
       Scan();//NormINV
       Scan();//Left Paren
       ParseTreeNodes.ParseTreeNode ARG1 = null;
       ParseTreeNodes.ParseTreeNode ARG2 = null;
       ParseTreeNodes.ParseTreeNode ARG3 = null;
       if(_Tok.GetToken() != ParseTreeNodes.Tokens.COMMA || _Tok.GetToken() != ParseTreeNodes.Tokens.RPAREN){
           ARG1 = ParseATreeNode(null);
           if(_Tok.GetToken() != ParseTreeNodes.Tokens.COMMA || _Tok.GetToken() != ParseTreeNodes.Tokens.RPAREN){
               ARG2 = ParseATreeNode(null);
               if(_Tok.GetToken()== ParseTreeNodes.Tokens.COMMA){
                   Scan();
                   ARG3 = ParseATreeNode(null);
                   if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
                       return new ParseTreeNodes.Variables.NormInvExprNode(ARG1,ARG2,ARG3);
                   }else{
                       _Errors.add("A NormINV statement was found without an ending right parenthesis.");
                       return new ParseTreeNodes.Variables.NormInvExprNode(ARG1,ARG2,ARG3);
                   }
               }else{
                   if(_Tok.GetToken()==ParseTreeNodes.Tokens.RPAREN){
                       return new ParseTreeNodes.Variables.NormInvExprNode(ARG1,ARG2);
                   }else{
                       _Errors.add("A NormINV statement was found without an ending right parenthesis.");
                       return new ParseTreeNodes.Variables.NormInvExprNode(ARG1,ARG2,null);
                   }
               }
           }else{
                if(_Tok.GetToken() == ParseTreeNodes.Tokens.COMMA){
                   Scan();
                   if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
                       return new ParseTreeNodes.Variables.NormInvExprNode(ARG1,null,null);
                   }else{
                       _Errors.add("A NormINV statement was found without an ending right parenthesis.");
                       return new ParseTreeNodes.Variables.NormInvExprNode(null,null,null);
                   }
               }else{
                  return new ParseTreeNodes.Variables.NormInvExprNode(ARG1,null); 
               }
           }
       }else{
           if(_Tok.GetToken() == ParseTreeNodes.Tokens.COMMA){
               Scan();
               if(_Tok.GetToken() == ParseTreeNodes.Tokens.RPAREN){
                   return new ParseTreeNodes.Variables.NormInvExprNode(null,null,null);
               }else{
                   _Errors.add("A NormINV statement was found without an ending right parenthesis.");
                   return new ParseTreeNodes.Variables.NormInvExprNode(null,null,null);
               }
           }else{
              return new ParseTreeNodes.Variables.NormInvExprNode(null,null); 
           }
       }
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
               break;
//           case LBRACKET:
//               break;
//           case RBRACKET:
//               break;
//           case CLBRACKET:
//               break;
//           case CRBRACKET:
//               break;
           case COLUMN:
               lefthandSide = FactorColumn();
               break;
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
               Scan(); //what if the next factor is an If statement? the if statement can produce a string...
               lefthandSide = new ParseTreeNodes.Strings.ConcatenateExprNode(lefthandSide, StringFactor());
               break;
           case RIGHT:
               lefthandSide = StringFactor();
               break;
           case LEFT:
               lefthandSide = StringFactor();
               break;
//           case LEN:
//               break;
//           case ROUND:
//               break;
//           case ROUNDUP:
//               break;
//           case ROUNDDOWN:
//               break;
           case LOG10:
               lefthandSide = FactorLog(true);
               break;
           case LN:
               lefthandSide = FactorLog(false);
               break;
           case RAND:
               lefthandSide = FactorRand();
               break;
           case RANDINT:
               lefthandSide = FactorRandInt();
               break;
//           case RANDBETWEEN:
//               break;
           case RANDINTBETWEEN:
               lefthandSide = FactorRandIntBetween();
               break;
           case NORMINV:
               lefthandSide = FactorNormINV();
               break;
//           case TRIINV:
//               break;
             case SUBSTRING:
                 lefthandSide = FactorSubString();
                 Scan();
                 break;
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
    private ParseTreeNodes.ParseTreeNode Parse(){
       Scan();
       ParseTreeNodes.ParseTreeNode Result = ParseATreeNode(null);
       return Result;
    }
    public ParseTreeNodes.ParseTreeNode Parse(String Expression){
        java.io.InputStream is = new java.io.ByteArrayInputStream(Expression.getBytes());
        _Scanner = new Scanner.Scanner(is);
        _Scanner.addObserver(this);
        Scan();
        ParseTreeNodes.ParseTreeNode Result = ParseATreeNode(null);
        return Result;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof String){
            _Errors.add(arg.toString());
        }else if(arg instanceof ParseTreeNodes.Token){
            //using the scan() method to identify when tokens are found rather than a report from the scanner...
            //this.setChanged();
            //this.notifyObservers(arg);
        }else{
            //ignored.
        }
        
    }
}
