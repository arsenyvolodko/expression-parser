package expression.exceptions;

import expression.CommonExpression;
import expression.Divide;

public class CheckedDivide extends Divide {

    public CheckedDivide(CommonExpression expression1, CommonExpression expression2) {
        super(expression1, expression2);
    }


    @Override
    protected int count(int exp1, int exp2) {

        if (exp2 == 0) {
            throw new DBZException("Division by zero");
        }

        if (exp2 == -1 && exp1 == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow");
        }

        return super.count(exp1, exp2);
    }


}
