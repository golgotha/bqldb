package com.numizmatclub.documentdb.parser.expression;

import com.numizmatclub.documentdb.storage.DocumentMatcher;
import org.bson.BsonDocument;

/**
 * @author Valerii Kantor
 */
public class BetweenExpression implements BooleanExpression {

    private final String field;
    private final ValueExpression start;
    private final ValueExpression end;

    public BetweenExpression(String field, ValueExpression start, ValueExpression end) {
        this.field = field;
        this.start = start;
        this.end = end;
    }


    @Override
    public boolean evaluate(BsonDocument document) {
        throw new UnsupportedOperationException("BetweenExpression is not implemented yet");
    }

    @Override
    public DocumentMatcher accept(BooleanExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}
