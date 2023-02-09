package expression;

public class Multiply extends AbstractExpressionPart implements CommonExpression {

    public Multiply(CommonExpression expression1, CommonExpression expression2) {
        super(expression1, expression2, " * ");
    }

    @Override
    protected int count(int exp1, int exp2) {
        return exp1 * exp2;
    }
}
