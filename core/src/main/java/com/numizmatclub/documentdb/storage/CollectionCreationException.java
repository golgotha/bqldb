package com.numizmatclub.documentdb.storage;

/**
 * @author Valerii Kantor
 */
public class CollectionCreationException extends StorageEngineException {
    public CollectionCreationException(String message) {
        super(message);
    }

    public CollectionCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
