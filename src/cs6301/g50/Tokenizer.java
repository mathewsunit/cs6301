/**
 * Created by
 * Group 50
 *
 * Varun Simha Balaraju
 * Venkata Sarath Chandra Prasad Nelapati
 * Jithin Paul
 * Sunit Mathew
 * on 8/25/2017.
 **/

package cs6301.g50;

public class Tokenizer {
    public enum Token { VAR, NUM, OP, EQ , OPEN, CLOSE, EOL, FAC }

    public static Token tokenize(String s) throws Exception {
        if(s.matches("\\d+")) {  // one or more digits
            return Token.NUM;
        } else if(s.matches("[a-z]")) {  // letter
            int index = s.charAt(0) - 'a';  // Convert var to index: a-z maps to 0-25
            return Token.VAR;
        } else if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%") || s.equals("^") || s.equals("|")) {
            return Token.OP;
        } else if(s.equals("=")) {
            return Token.EQ;
        } else if(s.equals("(")) {
            return Token.OPEN;
        } else if(s.equals(")")) {
            return Token.CLOSE;
        } else if(s.equals("!")) {
            return Token.FAC;
        } else if(s.equals(";")) {
            return Token.EOL;
        } else {  // Error
            throw new Exception("Unknown token: " + s);
        }
    }
}
