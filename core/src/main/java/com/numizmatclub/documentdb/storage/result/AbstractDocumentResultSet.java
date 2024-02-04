package com.numizmatclub.documentdb.storage.result;

import org.bson.BsonDocument;

/**
 * @author Valerii Kantor
 */
public abstract class AbstractDocumentResultSet implements ResultSet {

    @Override
    public int getInt(String fieldName) {
        return getDocument().getNumber(fieldName).intValue();
    }

    @Override
    public long getLong(String fieldName) {
        return getDocument().getNumber(fieldName).longValue();
    }

    @Override
    public String getString(String fieldName) {
        return getDocument().getString(fieldName).getValue();
    }

    @Override
    public String toJson() {
        return getDocument().toJson();
    }

    protected abstract BsonDocument getDocument();
}
