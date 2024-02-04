package com.numizmatclub.documentdb.storage;

public class OffsetExceededException extends StorageEngineException {

    public OffsetExceededException(String message) {
        super(message);
    }

    public OffsetExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}
