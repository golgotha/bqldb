package com.numizmatclub.documentdb.parser.expression;

import com.numizmatclub.documentdb.storage.DocumentMatcher;
import org.bson.BsonDocument;

public interface SQLExpression {

    Object execute(BsonDocument document);

    DocumentMatcher accept(SQLExpressionVisitor visitor);
}
