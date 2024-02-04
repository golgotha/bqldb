package com.numizmatclub.documentdb.parser.expression;

import com.numizmatclub.documentdb.parser.function.SQLFunction;
import org.bson.BsonDocument;

import java.util.List;


public final class FunctionExpression implements ValueExpression {

    private final SQLFunction sqlFunction;
    private final List<ValueExpression> parameters;
    private final boolean isAllFields;
    private String alias;

    public FunctionExpression(SQLFunction sqlFunction,
                              List<ValueExpression> parameters,
                              boolean isAllFields) {
        this.sqlFunction = sqlFunction;
        this.parameters = parameters;
        this.isAllFields = isAllFields;
    }

    @Override
    public Object execute(BsonDocument document) {
        List<Object> params = parameters.stream()
                .map(parameter -> parameter.execute(document))
                .toList();

        return sqlFunction.execute(params);
    }

    public List<ValueExpression> getParameters() {
        return parameters;
    }

    public boolean isAllFields() {
        return isAllFields;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFunctionName() {
        return sqlFunction.getName();
    }
}
