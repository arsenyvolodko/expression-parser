package expression;


public interface CommonExpression {

    int evaluate(int num);

    int evaluate();

    int evaluate(int x, int y, int z);

    boolean equals(Object o);

    String toString();

}
