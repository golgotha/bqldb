package com.numizmatclub.documentdb.parser;

import com.numizmatclub.documentdb.DatabaseSession;
import com.numizmatclub.documentdb.index.IndexField;
import com.numizmatclub.documentdb.index.PropertyIndexDefinition;
import com.numizmatclub.documentdb.storage.result.EmptyResultSet;
import com.numizmatclub.documentdb.storage.result.ResultSet;

import java.util.List;

/**
 * @author Valery Kantor
 */
public class CreateIndexStatement implements SqlStatement {

    private final String collection;
    private final String name;
    private final List<IndexField> indexFields;

    public CreateIndexStatement(String collection,
                                String name,
                                List<IndexField> indexFields) {
        this.collection = collection;
        this.name = name;
        this.indexFields = indexFields;
    }

    public String getCollection() {
        return collection;
    }

    public String getName() {
        return name;
    }

    public List<IndexField> getFields() {
        return indexFields;
    }

    @Override
    public ResultSet execute(DatabaseSession session) {
        session.getMetadata()
                .getIndexManager()
                .createIndex(
                        getCollection(),
                        getName(),
                        new PropertyIndexDefinition(getFields().get(0))
                );

        return new EmptyResultSet();
    }

    @Override
    public void accept(SqlStatementVisitor visitor) {
        visitor.visit(this);
    }
}
