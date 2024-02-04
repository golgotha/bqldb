package com.numizmatclub.documentdb.parser;

import com.numizmatclub.documentdb.DatabaseSession;
import com.numizmatclub.documentdb.function.evaluator.CountFunctionEvaluator;
import com.numizmatclub.documentdb.parser.expression.FunctionExpression;
import com.numizmatclub.documentdb.parser.expression.ValueExpression;
import com.numizmatclub.documentdb.storage.DocumentMatcher;
import com.numizmatclub.documentdb.storage.ExpressionDocumentMatcher;
import com.numizmatclub.documentdb.storage.result.FilteredResultSet;
import com.numizmatclub.documentdb.storage.result.ResultSet;

import java.util.List;
import java.util.Optional;

public class SelectStatement implements SqlStatement {

    private final String from;
    private final WhereClause where;
    private final List<ValueExpression> projection;
    private final Long limit;
    private final Long offset;

    public SelectStatement(String from,
                           WhereClause where,
                           List<ValueExpression> projection,
                           Long limit,
                           Long offset) {
        this.from = from;
        this.where = where;
        this.projection = projection;
        this.limit = limit;
        this.offset = offset;
    }

    public String getFrom() {
        return from;
    }

    public WhereClause getWhere() {
        return where;
    }

    public List<ValueExpression> getProjection() {
        return projection;
    }

    public Long getLimit() {
        return limit;
    }

    public Long getOffset() {
        return offset;
    }

    @Override
    public ResultSet execute(DatabaseSession session) {
        final String collection = getFrom();
        WhereClause where = getWhere();
        DocumentMatcher documentMatcher = where != null
                ? new ExpressionDocumentMatcher(where)
                : document -> true;

        List<ValueExpression> projectionFieldNames = getProjection();
        Optional<FunctionExpression> countFunction = findCountFunction();

        ResultSet filteredResultSet = new FilteredResultSet(
                session.getStorage(),
                documentMatcher,
                collection,
                projectionFieldNames,
                getOffset(),
                getLimit()
        );

        if (countFunction.isPresent()) {
            return new CountFunctionEvaluator(filteredResultSet, countFunction.get()).evaluate();
        } else {
            return filteredResultSet;
        }
    }

    @Override
    public void accept(SqlStatementVisitor visitor) {
        visitor.visit(this);
    }

    private Optional<FunctionExpression> findCountFunction() {
        return getProjection().stream()
                .filter(projection -> projection instanceof FunctionExpression function
                        && function.getFunctionName().equals("count"))
                .map(FunctionExpression.class::cast)
                .findFirst();
    }
}
