package com.numizmatclub.documentdb.parser.expression;

import com.numizmatclub.documentdb.storage.DocumentMatcher;
import org.bson.BsonDocument;

import java.util.Objects;

/**
 * @author Valerii Kantor
 */
public class OrConditionExpression implements BooleanExpression {
    private final BooleanExpression left;
    private final BooleanExpression right;

    public OrConditionExpression(BooleanExpression left, BooleanExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(BsonDocument document) {
        return left.evaluate(document) || right.evaluate(document);
    }

    @Override
    public DocumentMatcher accept(BooleanExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrConditionExpression that = (OrConditionExpression) o;
        return Objects.equals(left, that.left) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
