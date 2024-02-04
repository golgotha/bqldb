package com.numizmatclub.documentdb.parser;

public interface QueryParser {

    SqlStatement parse(String query);
}
