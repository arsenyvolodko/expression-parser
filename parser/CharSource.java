package expression.parser;

public interface CharSource {
    boolean hasNext();

    boolean isDigit();

    boolean isVar();

    char next();

    char getCh();

    char getCh(int i);

    int getInd();
}
