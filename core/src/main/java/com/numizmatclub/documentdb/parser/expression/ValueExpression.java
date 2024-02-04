package com.numizmatclub.documentdb.parser.expression;

import org.bson.BsonDocument;

/**
 * @author Valerii Kantor
 */
public interface ValueExpression {

    Object execute(BsonDocument document);

    String getAlias();

    void setAlias(String alias);

}
