package com.numizmatclub.documentdb.parser.expression;

import org.bson.BsonDocument;

/**
 * Defines an interface for value expressions for a query.
 * Value expression can be constant value, time, function and etc.
 *
 * @author Valerii Kantor
 */
public interface ValueExpression {

    Object execute(BsonDocument document);

    String getAlias();

    void setAlias(String alias);

}
