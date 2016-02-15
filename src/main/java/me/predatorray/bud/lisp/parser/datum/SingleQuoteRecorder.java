package me.predatorray.bud.lisp.parser.datum;

import me.predatorray.bud.lisp.lexer.SingleQuoteToken;
import me.predatorray.bud.lisp.lexer.Token;
import me.predatorray.bud.lisp.lexer.TokenVisitorAdapter;

import java.util.HashMap;
import java.util.Map;

class SingleQuoteRecorder extends TokenVisitorAdapter {

    private Token former;
    private final Map<Token, SingleQuoteToken> followingSingleQuote = new HashMap<>();

    @Override
    protected void visitDefault(Token token) {
        if (former != null && former instanceof SingleQuoteToken) {
            followingSingleQuote.put(token, ((SingleQuoteToken) former));
        }
        former = token;
    }

    public SingleQuoteToken getAndConsumeTheSingleQuotePrefix(Token token) {
        SingleQuoteToken singleQuoteToken = followingSingleQuote.get(token);
        if (singleQuoteToken == null) {
            return null;
        }
        followingSingleQuote.remove(token);
        return singleQuoteToken;
    }
}
