package expression.exceptions;


public class BaseParser {
    private static final char END = '\0';
    private final CharSource source;
    private char ch = 0xffff;

    protected BaseParser(final CharSource source) {
        this.source = source;
        take();
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        //System.out.println(ch);
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }


    protected boolean isDigit() {
        return source.isDigit();
    }

    protected boolean isVar() {
        return source.isVar();
    }

    protected boolean isVar(char c) {
        return c == 'x' || c == 'y' || c == 'z';
    }

    protected int getInd() {
        return source.getInd();
    }

    protected boolean isOperator(char s) {
        return s == '*' || s == '/' || s == '-' || s == '+';
    }

    protected char getCh() {
        return source.getCh();
    }

    protected char getCh(int i) {
        return source.getCh(i);
    }

    protected boolean eof() {
        return take(END);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
}