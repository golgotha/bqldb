package com.numizmatclub.documentdb.parser;

public interface SqlStatementVisitor {

    void visit(SelectStatement statement);

    void visit(CreateIndexStatement statement);

    void visit(CreateCollectionStatement statement);
}
