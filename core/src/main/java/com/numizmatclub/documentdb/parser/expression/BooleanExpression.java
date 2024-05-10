package com.numizmatclub.documentdb.parser.expression;

import com.numizmatclub.documentdb.storage.DocumentMatcher;
import org.bson.BsonDocument;

/**
 * Defines an interface for boolean expressions for a query.
 *
 * @author Valerii Kantor
 */
public interface BooleanExpression {

    /**
     * Evaluate a result of expression.
     *
     * @param document the document that used for evaluation.
     * @return the result value of evaluation.
     */
    boolean evaluate(BsonDocument document);

    DocumentMatcher accept(BooleanExpressionVisitor visitor);

}
