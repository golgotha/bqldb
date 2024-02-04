package com.numizmatclub.documentdb.index;

/**
 * @author Valerii Kantor
 */
public class InvalidIndexCollectionNameException extends RuntimeException {

    public InvalidIndexCollectionNameException(String message) {
        super(message);
    }
}
