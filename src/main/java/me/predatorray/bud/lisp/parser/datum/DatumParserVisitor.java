package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.BooleanToken;
import me.predatorray.bud.lisp.lexer.CharacterToken;
import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.lexer.LeftParenthesis;
import me.predatorray.bud.lisp.lexer.NumberToken;
import me.predatorray.bud.lisp.lexer.RightParenthesis;
import me.predatorray.bud.lisp.lexer.SingleQuoteToken;
import me.predatorray.bud.lisp.lexer.StringToken;
import me.predatorray.bud.lisp.lexer.TextLocation;
import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.lexer.TokenVisitor;
import me.predatorray.bud.lisp.parser.ParserException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DatumParserVisitor implements TokenVisitor {

    private final ParenthesisChecker parenthesisChecker = new ParenthesisChecker();
    private final SingleQuoteRecorder singleQuoteRecorder = new SingleQuoteRecorder();

    private final Stack<List<Datum>> dataStack;
    private final List<Datum> bottom;

    public DatumParserVisitor() {
        dataStack = new Stack<>();
        bottom = new LinkedList<>();
        dataStack.push(bottom);
    }

    private void appendOnTopOfStack(Datum datum) {
        List<Datum> top = dataStack.peek();
        if (top == null) {
            throw new ParserException("parentheses are not balanced");
        }
        top.add(datum);
        if (top == bottom) {
            rootDatumReady(datum);
        }
    }

    protected void rootDatumReady(Datum datumAtStackBottom) {
        // does nothing
    }

    private void appendOnTopOfStackQuoteIfRequired(Datum datum, Token token) {
        SingleQuoteToken singleQuote = singleQuoteRecorder.getAndConsumeTheSingleQuotePrefix(token);
        Datum each = datum;
        while (singleQuote != null) {
            each = quote(each, singleQuote);
            singleQuote = singleQuoteRecorder.getAndConsumeTheSingleQuotePrefix(singleQuote);
        }
        appendOnTopOfStack(each);
    }

    private CompoundDatum quote(Datum datum, SingleQuoteToken singleQuote) {
        TextLocation singleQuoteLocation = singleQuote.getLocation();
        LeftParenthesis generatedLp = new LeftParenthesis(singleQuoteLocation);
        return new CompoundDatum(Arrays.asList(
                new SymbolDatum(new IdentifierToken("quote", singleQuoteLocation)),
                datum), generatedLp);
    }

    @Override
    public void visit(LeftParenthesis leftParenthesis) {
        parenthesisChecker.visit(leftParenthesis);
        singleQuoteRecorder.visit(leftParenthesis);

        dataStack.push(new LinkedList<Datum>());
    }

    @Override
    public void visit(RightParenthesis rightParenthesis) {
        parenthesisChecker.visit(rightParenthesis);
        singleQuoteRecorder.visit(rightParenthesis);

        SingleQuoteToken quotePrefixOfRp = singleQuoteRecorder.getAndConsumeTheSingleQuotePrefix(rightParenthesis);
        if (quotePrefixOfRp != null) {
            throw new ParserException("Cannot quote a right parenthesis", quotePrefixOfRp);
        }

        LeftParenthesis leftParenthesis = parenthesisChecker.getLeftParenthesis(rightParenthesis);

        List<Datum> data = dataStack.pop();
        CompoundDatum compoundDatum = new CompoundDatum(data, leftParenthesis);

        appendOnTopOfStackQuoteIfRequired(compoundDatum, leftParenthesis);
    }

    @Override
    public void visit(SingleQuoteToken singleQuoteToken) {
        singleQuoteRecorder.visit(singleQuoteToken);
    }

    @Override
    public void visit(StringToken stringToken) {
        singleQuoteRecorder.visit(stringToken);
        appendOnTopOfStackQuoteIfRequired(new StringDatum(stringToken), stringToken);
    }

    @Override
    public void visit(BooleanToken booleanToken) {
        singleQuoteRecorder.visit(booleanToken);
        appendOnTopOfStackQuoteIfRequired(new BooleanDatum(booleanToken), booleanToken);
    }

    @Override
    public void visit(NumberToken numberToken) {
        singleQuoteRecorder.visit(numberToken);
        appendOnTopOfStackQuoteIfRequired(new NumberDatum(numberToken), numberToken);
    }

    @Override
    public void visit(CharacterToken characterToken) {
        singleQuoteRecorder.visit(characterToken);
        appendOnTopOfStackQuoteIfRequired(new CharacterDatum(characterToken), characterToken);
    }

    @Override
    public void visit(IdentifierToken identifierToken) {
        singleQuoteRecorder.visit(identifierToken);
        appendOnTopOfStackQuoteIfRequired(new SymbolDatum(identifierToken), identifierToken);
    }

    @Override
    public void visit(Token unknown) {
        throw new ParserException("unknown token: " + unknown);
    }

    public List<Datum> getRootData() {
        if (!parenthesisChecker.isBalanced()) {
            throw new ParserException("parentheses are not balanced");
        }
        return new ArrayList<>(bottom);
    }
}
