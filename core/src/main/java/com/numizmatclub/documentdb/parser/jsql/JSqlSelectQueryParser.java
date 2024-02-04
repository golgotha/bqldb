package com.numizmatclub.documentdb.parser.jsql;

import com.numizmatclub.documentdb.parser.SelectStatement;
import com.numizmatclub.documentdb.parser.SqlParserException;
import com.numizmatclub.documentdb.parser.SqlStatement;
import com.numizmatclub.documentdb.parser.WhereClause;
import com.numizmatclub.documentdb.parser.expression.BooleanExpression;
import com.numizmatclub.documentdb.parser.expression.ValueExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Limit;
import net.sf.jsqlparser.statement.select.Offset;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

import java.util.List;

public class JSqlSelectQueryParser {

    private JSqlSelectQueryParser() {
    }

    public static SqlStatement parse(Select select) {
        try {
            PlainSelect plainSelect = select.getSelectBody(PlainSelect.class);
            String from = plainSelect.getFromItem(Table.class).getName();

            Expression where = plainSelect.getWhere();

            SyntaxTreeBuilder syntaxTreeBuilder = new SyntaxTreeBuilder();
            WhereClause whereClause = null;
            if (where != null) {
                final BooleanExpression whereExpression = syntaxTreeBuilder.buildTree(where);
                whereClause = new WhereClause(whereExpression);
            }


            Limit limitExpression = plainSelect.getLimit();
            Long limit = limitExpression != null
                    ? limitExpression.getRowCount(LongValue.class).getValue()
                    : null;

            Offset offsetExpression = plainSelect.getOffset();
            Long offset = offsetExpression != null
                    ? offsetExpression.getOffset(LongValue.class).getValue()
                    : null;

            List<ValueExpression> projection = syntaxTreeBuilder.buildProjectionList(plainSelect);

            return new SelectStatement(from, whereClause, projection, limit, offset);
        } catch (Exception e) {
            throw new SqlParserException(e);
        }
    }

//    private static FunctionExpression mapFunctionExpression(Function function) {
//        boolean isAllFields = false;
//        List<SQLExpression> mappedParameters = new ArrayList<>();
//
//        List<Expression> parameters = function.getParameters().getExpressions();
//        for (Expression parameterExpression : parameters) {
//            if (parameterExpression instanceof AllColumns) {
//                isAllFields = true;
//            } else {
//                mappedParameters.add(mapExpression(parameterExpression));
//            }
//        }
//
//        String functionName = function.getName().toLowerCase();
//        return new FunctionExpression(functionName, mappedParameters, isAllFields);
//    }
}
