package com.numizmatclub.documentdb.parser.expression;

import com.numizmatclub.documentdb.storage.DocumentMatcher;
import org.bson.BsonDocument;

import java.util.Objects;

/**
 * @author Valerii Kantor
 */
public class BinaryConditionExpression implements BooleanExpression {
    private final ValueExpression left;
    private final CompareOperator operator;
    private final ValueExpression right;

    public BinaryConditionExpression(ValueExpression left,
                                     ValueExpression right,
                                     CompareOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public boolean evaluate(BsonDocument document) {
        return operator.execute(left.execute(document), right.execute(document));
    }

    @Override
    public DocumentMatcher accept(BooleanExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryConditionExpression that = (BinaryConditionExpression) o;
        return Objects.equals(left, that.left) && Objects.equals(operator, that.operator) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, operator, right);
    }
}
