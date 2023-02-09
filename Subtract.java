package expression;

public class Subtract extends AbstractExpressionPart implements CommonExpression {

    public Subtract(CommonExpression expression1, CommonExpression expression2) {
        super(expression1, expression2, " - ");
    }

    @Override
    protected int count(int exp1, int exp2) {
        return exp1 - exp2;
    }
}
