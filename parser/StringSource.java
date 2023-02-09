package expression.parser;


public class StringSource implements CharSource {
    private final String data;
    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public boolean isDigit() {
        return Character.isDigit(data.charAt(pos - 1));
    }

    @Override
    public boolean isVar() {
        return data.charAt(pos - 1) == 'x' || data.charAt(pos - 1) == 'y' || data.charAt(pos - 1) == 'z';
    }

    @Override
    public char getCh() {
        return data.charAt(pos - 1);
    }

    @Override
    public char getCh(int i) {
        return data.charAt(pos - (i + 1));
    }

    @Override
    public int getInd() {
        return pos - 1;
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }
}
