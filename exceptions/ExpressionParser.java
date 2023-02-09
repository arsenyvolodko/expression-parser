package expression.exceptions;

import expression.CommonExpression;
import expression.UnarLeading;
import expression.UnarTrail;
import expression.parser.BaseParser;
import expression.parser.CharSource;
import expression.parser.StringSource;


public final class ExpressionParser  {

    private static int BracketsBalance = 0;
    private static String expr = "";

    public static void showProblem(int l, int r, String e) throws ParserException {
        try {
            throw new ParserException(e + " in expression \"" + expr + "\": " +
                    expr.substring(0, l) + "|----->" + expr.substring(l, r) + "<-----|" + expr.substring(r));
        } catch (StringIndexOutOfBoundsException error) {
            if (l < 0 && r < 0) {
                showProblem(0, 0, e);
            } else if (l < 0 && r < expr.length()) {
                showProblem(0, r + 1, e);
            } else if (0 <= l && l < expr.length() && r >= expr.length()) {
                showProblem(l, expr.length() - 1, e);
            } else if (l < 0) {
                showProblem(0, expr.length() - 1, e);
            } else if (l >= expr.length() && r > expr.length()) {
                showProblem(expr.length(), expr.length(), e);
            }
        }
    }


    public CommonExpression parse(final String source) throws ParserException {
        expr = source;
        return parse(new StringSource(source));
    }

    public CommonExpression parse(final CharSource source) throws ParserException {
        return new Parser2(source).parseExpression();
    }


    private static class Parser2 extends BaseParser {

        public Parser2(final CharSource source) {
            super(source);
        }

        private CommonExpression parseExpression() throws ParserException {
            return FourthPriority();
        }

        private CommonExpression FourthPriority() throws ParserException {
            skipWhitespace();
            return makeExpression('n', 'x');
        }

        private CommonExpression ThirdPriority() throws ParserException {
            skipWhitespace();

            CommonExpression a = makeExpression('+', '-');

            if (!eof() && getCh() == ')' && (BracketsBalance < 1)) {
                BracketsBalance = 0;
                throw new ParserException("No opening parenthesis in expression \"" + expr + "\"");
            }
            return a;
        }

        private CommonExpression SecondPriority() throws ParserException {
            skipWhitespace();
            return makeExpression('*', '/');
        }

        private CommonExpression FirstPriority() throws ParserException {
            skipWhitespace();

            if (take(')')) {
                BracketsBalance = 0;
                if (eof()) {
                    showProblem(getInd(), getInd() + 1, "Argument expected, ')' found");
                }
                showProblem(getInd() - 1, getInd(), "Argument expected, ')' found");
            }

            if (isDigit()) {
                return new Const(parseNumber(false));
            }

            if (take('(')) {
                BracketsBalance += 1;
                CommonExpression lowest = FourthPriority();
                while (!(take(')')) && !(eof())) {
                }
                if (eof()) {
                    if (getCh() == ')') {
                        BracketsBalance -= 1;
                    }
                    if (BracketsBalance != 0) {
                        BracketsBalance = 0;
                        throw new ParserException("No closing parenthesis in expression \"" + expr + "\"");
                    }
                } else {
                    BracketsBalance -= 1;
                }

                return lowest;
            }

            switch (getCh()) {
                case 'l' -> {
                    return parseUnaryOperation('l');
                }
                case 't' -> {
                    return parseUnaryOperation('t');
                }
                case '-' -> {
                    return parseUnaryOperation('-');
                }
            }

            char s = take();
            if (isVar(s)) {
                return new Variable(String.valueOf(s));

            } else {
                BracketsBalance = 0;
                if (isOperator(s) || (int) s == 0) {

                    if ((int) s == 0) {
                        showProblem(expr.length(), expr.length(), "No argument found");
                    } else if (getInd() == 1) {
                        showProblem(getInd() - 2, getInd() - 2, "No argument found");
                    } else {
                        showProblem(getInd() - 2, getInd() - 1, "No argument found");
                    }

                } else if ((take('i') && take('n')) || (take('a') && take('x'))) {
                    showProblem(getInd() - 4, getInd() - 4, "No argument found");
                } else {
                    showProblem(getInd() - 1, getInd(), "Unexpected symbol");
                }
            }
            throw new ParserException("Impossible char in expression");
        }

        public CommonExpression checkUnary(char operation) throws ParserException {
            CommonExpression next;
            switch (getCh()) {
                case 'l' -> next = parseUnaryOperation('l');
                case 't' -> next = parseUnaryOperation('t');
                case '-' -> next = parseUnaryOperation('-');
                default -> {
                    showProblem(getInd() - 1, getInd(), "No argument found after unary operation");
                    throw new ParserException("Incorrect unary symbol");
                }

            }
            switch (operation) {
                case 'l' -> {
                    return new UnarLeading(next);
                }
                case 't' -> {
                    return new UnarTrail(next);
                }
                default -> {
                    return new CheckedNegate(next);
                }
            }
        }

        private CommonExpression parseUnaryOperation(char operation) throws ParserException {

            if (take(operation)) {
                if (take('0')) {
                    char c = getCh();
                    if (!(Character.isWhitespace(c) || c == '(' || c == ')')) {
                        if (eof()) {
                            showProblem(expr.length(), expr.length(), "No argument after unary operation t0/l0");
                        } else {
                            showProblem(getInd(), getInd() + 1, "No whitespace after t0/l0");
                        }
                    }
                }

                skipWhitespace();

                if (isDigit()) {
                    switch (operation) {
                        case 'l' -> {
                            return new UnarLeading(new Const(parseNumber(false)));
                        }
                        case 't' -> {
                            return new UnarTrail(new Const(parseNumber(false)));
                        }
                        case '-' -> {
                            return new Const(parseNumber(true));
                        }
                    }

                } else if (take('(')) {
                    BracketsBalance += 1;
                    CommonExpression first_level = FourthPriority();
                    while (!take(')')) ;
                    BracketsBalance -= 1;
                    switch (operation) {
                        case 'l' -> {
                            return new UnarLeading(first_level);
                        }
                        case 't' -> {
                            return new UnarTrail(first_level);
                        }
                        case '-' -> {
                            return new CheckedNegate(first_level);
                        }
                    }

                } else if (isVar()) {
                    switch (operation) {
                        case 'l' -> {
                            return new UnarLeading(new Variable(String.valueOf(take())));
                        }
                        case 't' -> {
                            return new UnarTrail(new Variable(String.valueOf(take())));
                        }
                        case '-' -> {
                            return new CheckedNegate(new Variable(String.valueOf(take())));
                        }
                    }
                } else {
                    return checkUnary(operation);
                }
            }
            showProblem(getInd() + 1, getInd() + 2, "No argument after minus");
            throw new ParserException("function showProblem doesn't work right");

        }


        private CommonExpression makeExpression(char operation1, char operation2) throws ParserException {

            CommonExpression left;
            boolean noWhitespace = false;

            if (operation1 == 'n') {
                left = ThirdPriority();
            } else if (operation1 == '+') {
                left = SecondPriority();
            } else {  // operation1 = '*'
                left = FirstPriority();
            }

            while (!eof()) {

                int ind = getInd();
                skipWhitespace();
                char supposedOperation = getCh();

                if (supposedOperation == 'm') {
                    if (getInd() - ind == 0 && getCh(1) != ')') {
                        noWhitespace = true;
                    }
                    take();

                    if (take('i') || take('a')) {
                        supposedOperation = getCh();
                        if (noWhitespace && (!Character.isWhitespace(getCh(-1)) || getCh(-1) != '(')) {
                            showProblem(getInd() - 3, getInd() + 2, "No whitespaces around min/max");
                        }
                    } else {
                        showProblem(getInd() - 3, getInd() + 2, "Unparsing symbol around min/max");
                    }
                }

                if (supposedOperation != operation1 && supposedOperation != operation2) {

                    if (!isOperator(supposedOperation) && supposedOperation != ')'
                            && supposedOperation != 'n' && supposedOperation != 'x') {
                        BracketsBalance = 0;
                        if (Character.isDigit(supposedOperation)) {
                            showProblem(getInd() - 1, getInd(), "Unexpected symbol");
                        }
                        showProblem(getInd(), getInd() + 1, "Unexpected symbol");
                    }
                    break;
                }

                if (take(')')) {
                    BracketsBalance -= 1;
                    if (BracketsBalance < 0) {
                        BracketsBalance = 0;
                        showProblem(getInd() - 2, getInd() - 1, "More closed brackets than opened");
                    }
                } else {
                    take();
                }

                CommonExpression right;
                if (operation1 == 'n') {
                    right = ThirdPriority();
                } else if (operation1 == '+') {
                    right = SecondPriority();
                } else { // operation1 = '*'
                    right = FirstPriority();
                }

                switch (supposedOperation) {
                    case '+' -> left = new CheckedAdd(left, right);
                    case '-' -> left = new CheckedSubtract(left, right);
                    case '*' -> left = new CheckedMultiply(left, right);
                    case '/' -> left = new CheckedDivide(left, right);
                    case 'n' -> left = new Min(left, right);
                    case 'x' -> left = new Max(left, right);

                }

            }

            if (getCh() == ')' && eof()) {
                if (BracketsBalance > 0) {
                    BracketsBalance = 0;
                    throw new ParserException("More opened brackets than closed");
                } else if (BracketsBalance < 0) {
                    BracketsBalance = 0;
                    throw new ParserException("More closed brackets than opened");
                }
            }
            return left;
        }


        private void skipWhitespace() throws ParserException {
            while (Character.isWhitespace(getCh()) && !eof()) {
                if (eof()) {
                    if (BracketsBalance > 0) {
                        BracketsBalance = 0;
                        throw new ParserException("More opened brackets than closed");
                    } else if (BracketsBalance < 0) {
                        BracketsBalance = 0;
                        throw new ParserException("More closed brackets than opened");
                    }
                }
                take();
            }
        }

        private int parseNumber(boolean sign) throws ParserException {

            final StringBuilder sb = new StringBuilder();
            takeInteger(sb);


            try {
                if (sign) {
                    return Integer.parseInt("-" + sb);
                }
                return Integer.parseInt(sb.toString());
            } catch (final NumberFormatException e) {
                if (sign) {
                    showProblem(expr.indexOf(String.valueOf(sb)) - 1, expr.indexOf(String.valueOf(sb)) + sb.length(), "Constant overflow");
                } else {
                    showProblem(expr.indexOf(String.valueOf(sb)), expr.indexOf(String.valueOf(sb)) + sb.length(), "Constant overflow");
                }
            }
            throw new ParserException("Incorrect number parsing");
        }

        private void takeDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(take());
            }
        }

        private void takeInteger(final StringBuilder sb) throws ParserException {
            if (take('-')) {
                sb.append('-');
                skipWhitespace();
            }
            if (take('0')) {
                sb.append('0');
            } else if (between('1', '9')) {
                takeDigits(sb);
            }
        }
    }
}
