package me.predatorray.bud.lisp.parser;

import me.predatorray.bud.lisp.lexer.IdentifierToken;
import me.predatorray.bud.lisp.util.Sets;
import me.predatorray.bud.lisp.util.Validation;

import java.util.Set;

public class Keyword extends TokenLocatedExpression {

    public static final Set<String> EXPRESSION_KEYWORDS = Sets.asSet(
            "quote", "lambda", "if", "set!", "begin", "cond", "and", "or", "case",
            "let", "let*", "letrec", "do", "delay", "quasiquote");

    public static final Set<String> SYNTACTIC_KEYWORDS = Sets.union(Sets.asSet(
            "else", "=>", "define", "unquote", "unquote-splicing"), EXPRESSION_KEYWORDS);

    public static Keyword getKeywordIfApplicable(IdentifierToken identifierToken) {
        Validation.notNull(identifierToken);
        if (SYNTACTIC_KEYWORDS.contains(identifierToken.getName())) {
            return new Keyword(identifierToken);
        } else {
            return null;
        }
    }

    private final String keywordName;

    public Keyword(IdentifierToken identifierToken) {
        super(identifierToken);
        this.keywordName = identifierToken.getName();
    }

    @Override
    public void accept(ExpressionVisitor expressionVisitor) {
        expressionVisitor.visit(this);
    }

    public String getKeywordName() {
        return keywordName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keyword keyword = (Keyword) o;

        return keywordName.equals(keyword.keywordName);

    }

    public boolean isQuote() {
        return "quote".equals(keywordName);
    }

    @Override
    public int hashCode() {
        return keywordName.hashCode();
    }
}
