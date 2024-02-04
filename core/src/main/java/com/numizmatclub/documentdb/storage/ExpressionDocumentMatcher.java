package com.numizmatclub.documentdb.storage;

import com.numizmatclub.documentdb.parser.WhereClause;
import org.bson.BsonDocument;

/**
 * @author Valerii Kantor
 */
public class ExpressionDocumentMatcher implements DocumentMatcher {

    private final WhereClause whereClause;

    public ExpressionDocumentMatcher(WhereClause whereClause) {
        this.whereClause = whereClause;
    }

    @Override
    public boolean matches(BsonDocument document) {
        return whereClause.matchFilters(document);
    }
}
