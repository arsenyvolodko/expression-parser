package expression.exceptions;

import expression.Add;
import expression.CommonExpression;

public class CheckedAdd extends Add {

    public CheckedAdd(CommonExpression expression1, CommonExpression expression2) {
        super(expression1, expression2);
    }

    @Override
    protected int count(int exp1, int exp2) {

        if ((exp1 < Integer.MIN_VALUE - exp2 && exp2 < 0) || (exp1 > Integer.MAX_VALUE - exp2 && exp2 >= 0)) {
            throw new OverflowException("Overflow");
        }

        return super.count(exp1, exp2);
    }


}
