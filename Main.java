package expression;

import expression.exceptions.ExpressionParser;
import expression.exceptions.ParserException;

public class Main {
    public static void main(String[] args) throws ParserException {
        ExpressionParser parser = new ExpressionParser();
        String expression = "2*(5+y)-((2-5)*x)/z";
        System.out.println(parser.parse(expression).evaluate(4, 5, 6));
    }
}
