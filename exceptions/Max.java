package expression.exceptions;

import expression.CommonExpression;

public class Max extends AbstractExpressionPart implements CommonExpression {

    public Max(CommonExpression expression1, CommonExpression expression2) {
        super(expression1, expression2, " max ");
    }

    @Override
    protected int count(int exp1, int exp2) {
        return Math.max(exp1, exp2);
    }

}
