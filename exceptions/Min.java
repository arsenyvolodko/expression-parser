package expression.exceptions;

import expression.CommonExpression;

public class Min extends AbstractExpressionPart implements CommonExpression {

    public Min(CommonExpression expression1, CommonExpression expression2) {
        super(expression1, expression2, " min ");
    }

    @Override
    protected int count(int exp1, int exp2) {
        return Math.min(exp1, exp2);
    }

}

