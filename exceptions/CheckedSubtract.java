package expression.exceptions;

import expression.CommonExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {

    public CheckedSubtract(CommonExpression expression1, CommonExpression expression2) throws OverflowException {
        super(expression1, expression2);
    }

    @Override
    protected int count(int exp1, int exp2) {

        if (exp2 > 0) {
            if (exp1 < Integer.MIN_VALUE + exp2) {
                throw new OverflowException("Overflow");
            }
        } else {
            if (exp1 > Integer.MAX_VALUE + exp2) {
                throw new OverflowException("Overflow");
            }
        }

        return super.count(exp1, exp2);
    }

}
