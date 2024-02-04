package com.numizmatclub.documentdb.parser;

public class SqlParserException extends RuntimeException {

    public SqlParserException(Throwable cause) {
        super(cause);
    }

    public SqlParserException(String message) {
        super(message);
    }

    public SqlParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
