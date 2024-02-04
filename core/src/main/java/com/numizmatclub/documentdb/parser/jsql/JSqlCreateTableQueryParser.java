package com.numizmatclub.documentdb.parser.jsql;

import com.numizmatclub.documentdb.parser.CreateCollectionStatement;
import com.numizmatclub.documentdb.parser.SqlStatement;
import net.sf.jsqlparser.statement.create.table.CreateTable;

/**
 * @author Valerii Kantor
 */
public class JSqlCreateTableQueryParser {

    public static SqlStatement parse(CreateTable createTable) {
        final String collectionName = createTable.getTable().getName();
        return new CreateCollectionStatement(collectionName);
    }

}
