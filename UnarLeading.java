package expression;


public class UnarLeading implements CommonExpression {

    CommonExpression ce;

    public UnarLeading(CommonExpression ce) {
        this.ce = ce;
    }

    public int evaluate(int x, int y, int z) {
        return Integer.numberOfLeadingZeros(ce.evaluate(x, y, z));
    }

    public int evaluate(int x) {
        return Integer.numberOfLeadingZeros(ce.evaluate(x));
    }

    @Override
    public int evaluate() {
        return Integer.numberOfLeadingZeros(ce.evaluate(0));
    }


    @Override
    public String toString() {
        return "l0(" + ce.toString() + ')';
    }

}
