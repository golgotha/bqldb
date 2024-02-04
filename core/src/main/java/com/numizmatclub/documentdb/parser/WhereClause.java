package com.numizmatclub.documentdb.parser;

import com.numizmatclub.documentdb.parser.expression.BooleanExpression;
import org.bson.BsonDocument;

import java.util.Objects;

/**
 * @author Valerii Kantor
 */
public class WhereClause {

    private final BooleanExpression whereExpression;

    public WhereClause(BooleanExpression whereExpression) {
        this.whereExpression = whereExpression;
    }

    public BooleanExpression getWhereExpression() {
        return whereExpression;
    }

    public boolean matchFilters(BsonDocument document) {
        if (whereExpression == null) {
            return true;
        }

        return whereExpression.evaluate(document);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhereClause that = (WhereClause) o;
        return Objects.equals(whereExpression, that.whereExpression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(whereExpression);
    }
}

