package expression.exceptions;

import expression.CommonExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {

    public CheckedMultiply(CommonExpression expression1, CommonExpression expression2) {
        super(expression1, expression2);
    }

    @Override
    protected int count(int exp1, int exp2) {

        if (exp2 > 0 && exp1 > 0 && Integer.MAX_VALUE / exp1 < exp2) {
            throw new OverflowException("Overflow");
        }
        if (exp2 < 0 && exp1 > 0 && Integer.MIN_VALUE / exp1 > exp2) {
            throw new OverflowException("Overflow");
        }
        if (exp2 > 0 && exp1 < 0 && Integer.MIN_VALUE / exp2 > exp1) {
            throw new OverflowException("Overflow");
        }
        if (exp2 < 0 && exp1 < 0 && Integer.MAX_VALUE / exp1 > exp2) {
            throw new OverflowException("Overflow");
        }


        return super.count(exp1, exp2);
    }


}
