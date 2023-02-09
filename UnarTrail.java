package expression;


public class UnarTrail implements CommonExpression {

    CommonExpression ce;

    public UnarTrail(CommonExpression ce) {
        this.ce = ce;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Integer.numberOfTrailingZeros(ce.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return Integer.numberOfTrailingZeros(ce.evaluate(x));
    }

    @Override
    public int evaluate() {
        return Integer.numberOfTrailingZeros(ce.evaluate(0));
    }

    @Override
    public String toString() {
        return "t0(" + ce.toString() + ')';
    }
}
