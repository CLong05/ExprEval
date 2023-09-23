/**
 * @Copyright(C) 2008 Software Engineering Laboratory (SELAB), Department of Computer
 * Science, SUN YAT-SEN UNIVERSITY. All rights reserved.
 **/

package parser;

import exceptions.ExpressionException;

/**
 * Main program of the expression based calculator ExprEval
 *
 * @author ³Âãñ£¨Ñ§ºÅ£º19335019£©
 * @version 1.00 (Last update: 2022.5.5)
 **/
public class Calculator {
    /**
     * The main program of the parser. 
     *
     * @param expression user input to the calculator from GUI.
     * @return if the expression is well-formed, return the evaluation result of it.
     * @throws ExpressionException if the expression has error, a corresponding
     *                             exception will be raised.
     **/
    public double calculate(String expression) throws ExpressionException {
        Parser parser = new Parser(expression);
        return parser.parse();
    }
}
