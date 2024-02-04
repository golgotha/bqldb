package com.numizmatclub.documentdb.parser.jsql;

import com.numizmatclub.documentdb.parser.QueryParser;
import com.numizmatclub.documentdb.parser.SqlParserException;
import com.numizmatclub.documentdb.parser.SqlStatement;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

public class JSqlQueryParser implements QueryParser {

    @Override
    public SqlStatement parse(String query) {
        try {
            SqlStatementVisitorAdapter visitorAdapter = new SqlStatementVisitorAdapter();
            Statement statement = CCJSqlParserUtil.parse(query);
            statement.accept(visitorAdapter);
            return visitorAdapter.getStatement();
        } catch (JSQLParserException e) {
            throw new SqlParserException(e);
        }
    }
}
