package com.numizmatclub.documentdb.storage;

import org.bson.BsonDocument;

/**
 * @author Valerii Kantor
 */
public interface DocumentMatcher {

    boolean matches(BsonDocument document);

}
