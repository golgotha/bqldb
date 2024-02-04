package com.numizmatclub.documentdb.parser;

import com.numizmatclub.documentdb.DatabaseSession;
import com.numizmatclub.documentdb.storage.result.ResultSet;

public interface SqlStatement {

    ResultSet execute(DatabaseSession session);

    void accept(SqlStatementVisitor visitor);
}
