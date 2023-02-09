package expression.exceptions;

import expression.CommonExpression;
import expression.Negate;

public class CheckedNegate extends Negate implements CommonExpression {

    CommonExpression ce;

    public CheckedNegate(CommonExpression ce) {
        this.ce = ce;
    }

    @Override
    public int evaluate(int num) {

        if (ce.evaluate(num) == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow");
        }

        return super.count(ce.evaluate(num));
    }

    @Override
    public int evaluate() {
        return super.count(ce.evaluate(0));
    }

    @Override
    public int evaluate(int x, int y, int z) {

        if (ce.evaluate(x, y, z) == Integer.MIN_VALUE) {
            throw new OverflowException("Overflow");
        }

        return super.count(ce.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "-(" + ce.toString() + ")";
    }
}
