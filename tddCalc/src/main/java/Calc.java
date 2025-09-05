import java.util.*;

public class Calc {

    private static int idx;
    private static String ParseRest;

    public static int run(String args) {
        ParseRest = args;
        idx = 0;
        return parseExpr();
    }

    private static int parseExpr(){
        int Eval = parseTerm();
        while(true){
            skipSpaces();
            if (match('+')) {
                Eval += parseTerm();
            }
            else if (match('-')) {
                Eval -= parseTerm();
            }
            else break;
        }
        return Eval;
    }

    private static int parseTerm(){
        int Tval = parseFactor();
        while(true){
            skipSpaces();
            if(match('*')){
                Tval *= parseFactor();
            } else if(match('/')){
                Tval /= parseFactor();
            }
            else break;
        }
        return Tval;
    }

    private static int parseFactor(){
        skipSpaces();
        if(match('+')) return parseFactor();
        if(match('-')) return -parseFactor();

        if (match('(')) {
            int Fval = parseExpr();
            skipSpaces();
            if(!match(')')){
                throw new IllegalArgumentException("')' expected at pos " + idx);
            }
            return Fval;
        }
        return parseNumber();
    }

    private static int parseNumber(){
        skipSpaces();
        if (idx >= ParseRest.length() || !Character.isDigit(ParseRest.charAt(idx))) {
            throw new IllegalArgumentException("Number expected at pos " + idx);
        }
        int num = 0;
        while(idx < ParseRest.length() && Character.isDigit(ParseRest.charAt(idx))){
            num = num*10 + (ParseRest.charAt(idx) - '0');
            idx++;
        }
        return num;
    }

    private static boolean match(char c){
        skipSpaces();
        if(idx < ParseRest.length() && ParseRest.charAt(idx) == c){
            idx++;
            return true;
        }
        return false;
    }
    private static void skipSpaces(){
        while(idx < ParseRest.length() && Character.isWhitespace(ParseRest.charAt(idx))) idx++;
    }

}
