package com.numizmatclub.documentdb.storage;

public class StorageEngineException extends RuntimeException {

    public StorageEngineException(String message) {
        super(message);
    }

    public StorageEngineException(String message, Throwable cause) {
        super(message, cause);
    }
}
