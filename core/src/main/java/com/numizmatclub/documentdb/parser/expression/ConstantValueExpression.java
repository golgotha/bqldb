package com.numizmatclub.documentdb.parser.expression;

import org.bson.BsonDocument;

import java.util.Objects;

/**
 * The static value implementation.
 *
 * @author Valerii Kantor
 */
public class ConstantValueExpression implements ValueExpression {

    private final Object value;
    private final Class<?> type;
    private String alias;

    public ConstantValueExpression(Object value, Class<?> type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public Object execute(BsonDocument document) {
        return value;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Class<?> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConstantValueExpression that = (ConstantValueExpression) o;
        return Objects.equals(value, that.value) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}
