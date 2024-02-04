package com.numizmatclub.documentdb.parser.expression;

import com.numizmatclub.documentdb.storage.DocumentMatcher;
import org.bson.BsonDocument;

import java.util.List;
import java.util.Objects;

/**
 * @author Valerii Kantor
 */
public class InConditionExpression implements BooleanExpression {

    private final String fieldName;
    private final List<ValueExpression> valueExpressions;

    public InConditionExpression(String fieldName, List<ValueExpression> valueExpressions) {
        this.fieldName = fieldName;
        this.valueExpressions = valueExpressions;
    }

    @Override
    public boolean evaluate(BsonDocument document) {
        throw new UnsupportedOperationException("In condition is not supported yet");
    }

    @Override
    public DocumentMatcher accept(BooleanExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InConditionExpression that = (InConditionExpression) o;
        return Objects.equals(valueExpressions, that.valueExpressions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueExpressions);
    }
}
