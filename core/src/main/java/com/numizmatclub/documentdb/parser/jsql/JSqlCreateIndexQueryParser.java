package com.numizmatclub.documentdb.parser.jsql;

import com.numizmatclub.documentdb.index.IndexField;
import com.numizmatclub.documentdb.index.Order;
import com.numizmatclub.documentdb.parser.CreateIndexStatement;
import com.numizmatclub.documentdb.parser.SqlStatement;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.Index;

import java.util.List;

public final class JSqlCreateIndexQueryParser {

    private JSqlCreateIndexQueryParser() {
    }

    public static SqlStatement parse(CreateIndex createIndex) {
        Table table = createIndex.getTable();
        String collection = table.getName();
        Index index = createIndex.getIndex();
        String name = index.getName();
        List<IndexField> indexFields = index.getColumns().stream()
                .map(column -> new IndexField(column.getColumnName(), getOrder(column)))
                .toList();

        return new CreateIndexStatement(collection, name, indexFields);
    }

    private static Order getOrder(Index.ColumnParams column) {
        Order order = Order.DEFAULT;

        if (column.getParams() == null) {
            return order;
        }

        List<String> params = column.getParams().stream()
                .map(String::toLowerCase)
                .toList();

        if (params.contains(Order.ASC.name().toLowerCase())) {
            order = Order.ASC;
        } else if (params.contains(Order.DESC.name().toLowerCase())) {
            order = Order.DESC;
        }

        return order;
    }
}
