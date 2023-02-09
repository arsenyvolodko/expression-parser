package expression;

import java.util.Objects;

public class Variable implements CommonExpression {

    private final String v;

    public Variable(String v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return v;
    }

    @Override
    public int evaluate(int num) {
        return num;
    }

    @Override
    public int evaluate() {
        return 0;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (v) {
            case "x" -> x;
            case "y" -> y;
            default -> z;
        };
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(v, variable.v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(v);
    }
}
