/*
 * Copyright (C) 2016 Ruslan Feshchenko
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tclinterpreter;

/**
 * A special lexer class for expressions
 *
 * @author Ruslan Feshchenko
 * @version 0.1
 */
public class TclExpressionLexer extends AbstractTclLexer {

    /**
     * Constructor
     *
     * @param script
     */
    public TclExpressionLexer(String script) {
        super(script);
    }
    
    /**
     * Reading a real number from the script
     *
     * @return
     */
    protected String readNumber() {
        StringBuilder number = new StringBuilder("");
        /*
         This is a number if didgit, dot and exponetial characters are present     
         */
        while (Character.isDigit(currentchar)
                || currentchar == '.'
                || (Character.toLowerCase(currentchar) == 'e'
                && (peek() == '-') || peek() == '+')
                || currentchar == '\\') {
            number.append(currentchar);
            advancePosition();
            if (Character.toLowerCase(currentchar) == 'e') {
                advancePosition();
                number.append(currentchar);
            }
        }
        return number.toString();
    }
    
    @Override
    public TclToken getToken() {
        /*
        Skipping any whitespace
        */
        if (Character.isWhitespace(currentchar)) {
            /*
             Returning a real number token
             */
            skipWhitespace();
        } 
        
        if (Character.isDigit(currentchar)) {
            /*
             Returning a real number token
             */
            return new TclToken(TclTokenType.REALNUMBER).setValue(readNumber());
        } else if (currentchar == '+') {
            /*
             Returning a plus op token
             */
            advancePosition();
            return new TclToken(TclTokenType.PLUS);
        } else if (currentchar == '-') {
            /*
             Returning a minus op token
             */
            advancePosition();
            return new TclToken(TclTokenType.MINUS);
        } else if (currentchar == '*') {
            /*
             Returning a multiplication op token
             */
            advancePosition();
            return new TclToken(TclTokenType.MUL);
        } else if (currentchar == '/') {
            /*
             Returning a division op token
             */
            advancePosition();
            return new TclToken(TclTokenType.DIV);
        } else if (currentchar == '(') {
            /*
             Returning a left paranthesis op token
             */
            advancePosition();
            return new TclToken(TclTokenType.LEFTPAR);
        } else if (currentchar == ')') {
            /*
             Returning a right paranthesis op token
             */
            advancePosition();
            return new TclToken(TclTokenType.RIGHTPAR);
        } else {
            /*
             Returning an unknown token
             */
            advancePosition();
            return new TclToken(TclTokenType.UNKNOWN);
        }
    }
}