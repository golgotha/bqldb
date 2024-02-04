package com.numizmatclub.documentdb.parser;

import com.numizmatclub.documentdb.DatabaseSession;
import com.numizmatclub.documentdb.storage.StorageEngine;
import com.numizmatclub.documentdb.storage.result.EmptyResultSet;
import com.numizmatclub.documentdb.storage.result.ResultSet;

/**
 * @author Valerii Kantor
 */
public class CreateCollectionStatement implements SqlStatement {

    private final String name;

    public CreateCollectionStatement(String name) {
        this.name = name;
    }

    @Override
    public ResultSet execute(DatabaseSession session) {
        StorageEngine storage = session.getStorage();
        storage.createCollection(name);
        return new EmptyResultSet();
    }

    @Override
    public void accept(SqlStatementVisitor visitor) {
        visitor.visit(this);
    }
}
