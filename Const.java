package expression;

import java.util.Objects;

public class Const implements CommonExpression {

    private final int c;

    public Const(int c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return String.valueOf(c);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return c;
    }

    @Override
    public int evaluate(int num) {
        return c;
    }

    @Override
    public int evaluate() {
        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return c == aConst.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(c);
    }
}
