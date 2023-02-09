package expression.exceptions;


import expression.CommonExpression;

import java.util.Objects;

abstract class AbstractExpressionPart implements CommonExpression {

    private final CommonExpression exp1;
    private final CommonExpression exp2;
    private final String operation;


    public AbstractExpressionPart(CommonExpression exp1, CommonExpression exp2, String operation) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }


    protected abstract int count(int exp1, int exp2);


    @Override
    public int evaluate(int num) {
        return count(exp1.evaluate(num), exp2.evaluate(num));
    }

    @Override
    public int evaluate() {
        return count(exp1.evaluate(0), exp2.evaluate(0));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return count(exp1.evaluate(x, y, z), exp2.evaluate(x, y, z));
    }


    @Override
    public String toString() {
        return "(" + exp1.toString() + operation + exp2.toString() + ")";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractExpressionPart that = (AbstractExpressionPart) o;

        return exp1.equals(that.exp1) && exp2.equals(that.exp2) && operation.equals(that.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exp1, exp2, operation);
    }

}
