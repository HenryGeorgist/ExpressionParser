/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scanner;
import java.util.Observable;
/**
 *
 * @author Will_and_Sara
 */
public class Scanner extends Observable {
    private java.io.InputStreamReader _sr;
    private boolean _putback = true;
    private boolean _eof = false;
    private char _c;
    private int _line = 1;
    private int _pos = 0;
    //public event TokenFound(int position, ParseTreeNodes.Tokens Tok, string text, string helpdocpath);
    //public event ErrorFound(string message);
    public Scanner(java.io.InputStream stream){
        _sr = new java.io.InputStreamReader(stream);
    }
    private char GetCharacter(){
        char[] read= new char[1];
        int result = 0;
        try {
            result = _sr.read(read,0,1);
            if(result == -1){
                _putback = true;
                _eof = true;}
        } catch (java.io.IOException ex) {
            return "\u0000".toCharArray()[0];//i dont think this is right
        }
        _pos++;
        return read[0];
    }
    private String BuildID(){
        String s = "";
        do{
            s+=_c;//Character.toString(_c)
            _c = GetCharacter();
        }while(Character.isLetter(_c)| Character.isDigit(_c)| "_".equals(Character.toString(_c)));//check if it is a letter a digit or an underbar.
        _putback = true;
        return s;
    }
    private String BuildSingleQuotedString(){
        String s = "";
        _c = GetCharacter();//get rid of the quote character
        do{
            s+=_c;//Character.toString(_c)
            _c = GetCharacter();
        }while(!"'".equals(Character.toString(_c)) & !_eof); //infinate loop, need to add the exit character in case the end quote is not supplied.
        _c = GetCharacter(); //get rid of the end quote.
        return s;
    }
    private String BuildDoubleQuotedString(){
        String s = "";
        _c = GetCharacter();//get rid of the quote character
        do{
            s+=_c;//Character.toString(_c)
            _c = GetCharacter();
        }while(!"\"".equals(Character.toString(_c)) & !_eof); //infinate loop, need to add the exit character in case the end quote is not supplied.
        _c = GetCharacter(); //get rid of the end quote.
        return s;
    }
    private NumberParseResult BuildNum(){
        String s="";
        boolean hasDecimal =false;
        boolean isNumerical = true;
        do{
            if(".".equals(Character.toString(_c))){
                if(hasDecimal==true){
                    isNumerical = false;
                }else{
                    hasDecimal=true;
                }              
            }
            s+=_c;
            _c = GetCharacter();
        }while(Character.isDigit(_c)| ".".equals(Character.toString(_c)));
        _putback = true;
        return new NumberParseResult(s,hasDecimal,isNumerical);
    }
    private ParseTreeNodes.Token BuildOperator(){
        int pos = _pos;
        switch (Character.toString(_c)) {
            case "="://what about =< or =>
                _c = GetCharacter();
                if(!" ".equals(Character.toString(_c))){_putback = true;}
                return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.EQ, "=", _line, pos);  
            case "&":
                _c = GetCharacter();
                if(!" ".equals(Character.toString(_c))){_putback = true;}
                return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.ANDPERSTAND, "&", _line, pos);
            case "+":
                _c = GetCharacter();
                if(!" ".equals(Character.toString(_c))){_putback = true;} 
                return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.PLUS, "+", _line, pos, "C:/Users/Q0HECWPL/Perforce/HEC/DotNet/FieldCalculationParser/FieldCalculationParser/Resources/Plus.html");
            case "-":
                _c = GetCharacter();
                if(!" ".equals(Character.toString(_c))){_putback = true;} 
                return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.MINUS, "-", _line, pos, "http://github.com");
            case "/":
                _c = GetCharacter();
                if(!" ".equals(Character.toString(_c))){_putback = true;}
                return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.DIVIDE, "/", _line, pos, "http://timcohn.com/TAC_Software/PeakfqSA/");
             case "*":
                _c = GetCharacter();
                if(!" ".equals(Character.toString(_c))){_putback = true;}
                return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.TIMES, "*", _line, pos);
             case "^":
                _c = GetCharacter();
                if(!" ".equals(Character.toString(_c))){_putback = true;}  
                return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.EXPONENT, "^", _line, pos);
             case "<":
                _c = GetCharacter();
                if("=".equals(Character.toString(_c))){
                    _c = GetCharacter();
                    if(!" ".equals(Character.toString(_c))){_putback = true;}
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.LTE, "<=", _line, pos);
                }else{
                    if(!" ".equals(Character.toString(_c))){_putback = true;}
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.LT, "<", _line, pos); 
                }
             case ">":
                _c = GetCharacter();
                if("=".equals(Character.toString(_c))){
                    _c = GetCharacter();
                    if(!" ".equals(Character.toString(_c))){_putback = true;}
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.GTE, ">=", _line, pos);
                }else{
                    if(!" ".equals(Character.toString(_c))){_putback = true;}
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.GT, ">", _line, pos); 
                }
             case "!":
                _c = GetCharacter();
                if("=".equals(Character.toString(_c))){
                    _c = GetCharacter();
                    if(!" ".equals(Character.toString(_c))){_putback = true;}
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.NEQ, "!=", _line, pos);
                }else{
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.ERR, "! was not followed by =", _line, pos); 
                }                 
            default:
                _putback = true;
                this.setChanged();
                this.notifyObservers("Found a space at location " + pos + "outside of quotes (single or double) without an operator following");
                return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.ERR, "Found a space outside of quotes (single or double) without an operator following", _line, pos);
        }
    }
    private ParseTreeNodes.Tokens KeywordLookup(String ID){
        String toUpperCase = ID.toUpperCase();
        switch(toUpperCase){
            case "IF":
                return ParseTreeNodes.Tokens.IF;
            case "AND":
                return ParseTreeNodes.Tokens.AND;
            case "OR":
                return ParseTreeNodes.Tokens.OR;
            case ",":
                return ParseTreeNodes.Tokens.COMMA;
            case "TRUE":
                return ParseTreeNodes.Tokens.BOOLLIT;
            case "FALSE":
                return ParseTreeNodes.Tokens.BOOLLIT;
            case "RIGHT":
                return ParseTreeNodes.Tokens.RIGHT;
            case "LEFT":
                return ParseTreeNodes.Tokens.LEFT;
            case "LEN":
                return ParseTreeNodes.Tokens.LEN;
            case "RAND":
                return ParseTreeNodes.Tokens.RAND;
            case "RANDBETWEEN":
                return ParseTreeNodes.Tokens.RANDBETWEEN;
            case "RANDINT":
                return ParseTreeNodes.Tokens.RANDINT;
            case "RANDINTBETWEEN":
                return ParseTreeNodes.Tokens.RANDINTBETWEEN;
            case "ROUND":
                return ParseTreeNodes.Tokens.ROUND;
            case "ROUNDDOWN":
                return ParseTreeNodes.Tokens.ROUNDDOWN;
            case "ROUNDUP":
                return ParseTreeNodes.Tokens.ROUNDUP;
            case "NORMINV":
                return ParseTreeNodes.Tokens.NORMINV;
            case "TRIINV":
                return ParseTreeNodes.Tokens.TRIINV;
            case "SUBSTRING":
                return ParseTreeNodes.Tokens.SUBSTRING;
            case "CONTAINS":
                return ParseTreeNodes.Tokens.CONTAINS;
            case "OFFSET":
                return ParseTreeNodes.Tokens.OFFSET;
            case "DBL":
                return ParseTreeNodes.Tokens.CONVERTTODOUBLE;
            case "STR":
                return ParseTreeNodes.Tokens.CONVERTTOSTRING;
            case "INT":
                return ParseTreeNodes.Tokens.CONVERTTOINT;
            case "SHORT":
                return ParseTreeNodes.Tokens.CONVERTTOSHORT;
            case "BOOL":
                return ParseTreeNodes.Tokens.CONVERTTOBOOLEAN;
            case "BYTE":
                return ParseTreeNodes.Tokens.CONVERTTOBYTE;
            default :
                return ParseTreeNodes.Tokens.STRINGLIT;
        }
        
    }
    public ParseTreeNodes.Token Scan(){
        if(_eof){
            //this.setChanged();
            //this.notifyObservers("The Scan function was called after the end of the file. Some expression is likely incomplete.");
            return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.EOF,"The Scanner was called after the end of the file, some expression must be incomplete.",_line,_pos);
        }
        if(_putback){
            _putback = false;
            if(_c=='\u0000' && _pos==0){_c=GetCharacter();}
        }else{
            _c = GetCharacter();
        }
        int IdPos = _pos;
        String Result = null;
        if(Character.isLetter(_c) | "_".equals(Character.toString(_c))){
            Result = BuildID();
            return new ParseTreeNodes.Token(KeywordLookup(Result), Result, _line, IdPos);
        }else if(Character.isDigit(_c)| ".".equals(Character.toString(_c))){
            NumberParseResult num = BuildNum();
            if(num.IsNumerical){
                if(num.HasDecimal){
                   //double 
                   return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.NUMLIT,num.Str,_line,IdPos);
                }else{
                    //integer
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.NUMLITINT,num.Str,_line,IdPos);
                }
            }else{
                return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.STRINGLIT,num.Str,_line,IdPos);
            }

        }else{
            String C =Character.toString(_c);
            switch (C){
                case "(":
                  return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.LPAREN,C,_line,_pos);
                case ")":
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.RPAREN, C, _line, _pos);
                case "{":
                    //index 
                    int indexpos = _pos;
                    ParseTreeNodes.Token index = Scan();
                    if(index.GetToken()==ParseTreeNodes.Tokens.STRINGLIT){
                        //check if the other bracket is there.
                        _c = GetCharacter();
                        if("}".equals(Character.toString(_c))){
                            return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.INDEX,index.GetString(),_line,indexpos);
                        }else{
                            this.setChanged();
                            this.notifyObservers("Encountered { followed by a string literal but no }");
                        return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.ERR, "Encounterd { followed by a string literal but no }", _line, indexpos);
                    }
                    }else{
                        this.setChanged();
                        this.notifyObservers("Encountered { followed by something that wasnt recognized as a string literal.");
                        return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.ERR, "Encounterd { followed by something that wasnt recognized as a string literal", _line, indexpos);
                    }
                case "[":
                    //column
                    int colpos = _pos;
                    ParseTreeNodes.Token ColumnName = Scan();
                    if(ColumnName.GetToken()==ParseTreeNodes.Tokens.STRINGLIT){
                        //check if the other bracket is there.
                        //_c = GetCharacter();
                        if("]".equals(Character.toString(_c))){
                            return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.COLUMN,ColumnName.GetString(),_line,colpos);
                        }else{
                            this.setChanged();
                            this.notifyObservers("Encountered [ followed by a string literal but no closing bracket ].");
                            return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.ERR, "Encounterd [ followed by a string literal but no ]", _line, colpos);
                        }
                    }else{
                        this.setChanged();
                        this.notifyObservers("Encountered [ followed by something that wasnt recognized as a string literal (specifcally a column name).");
                        return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.ERR, "Encounterd [ followed by something that wasnt recognized as a string literal", _line, colpos);
                    }
                case "+":
                    return BuildOperator();
                case "-":
                    return BuildOperator();
                case "*":
                    return BuildOperator();
                case "/":
                    return BuildOperator();
                case "^":
                    return BuildOperator();
                case "=":
                    return BuildOperator();
                case "!":
                    return BuildOperator();
                case "<":
                    return BuildOperator();
                case ">":
                    return BuildOperator();
                case ",":
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.COMMA,C,_line,_pos);
                case ".":
                    this.setChanged();
                    this.notifyObservers("A period was found outside of a number or a quoted string at line " + _line + " and position " + _pos);
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.ERR,C + " was found outside of a number or a string",_line,_pos);
                case "'":
                    //single quote, read until next single quote
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.STRINGLIT,BuildSingleQuotedString(),_line,_pos);
                case "\""://double quote.
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.STRINGLIT,BuildDoubleQuotedString(),_line,_pos);
                case "&":
                    return BuildOperator();
                case " ":
                    _c = GetCharacter();
                    return BuildOperator();
                case ""://this should catch the end of file, but it doesnt.
                    return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.EOF,"End of File", _line,_pos);
                default:
                   return new ParseTreeNodes.Token(ParseTreeNodes.Tokens.ERR, C+" is not a valid character", _line, _pos); 
            }
        }
    }

}
