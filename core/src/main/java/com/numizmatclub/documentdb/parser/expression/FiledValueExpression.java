package com.numizmatclub.documentdb.parser.expression;

import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.Objects;

import static com.numizmatclub.documentdb.utils.BsonValueUtils.getBsonValue;

/**
 * The Field value implementation. Resolve document value for field name.
 *
 * @author Valerii Kantor
 */
public class FiledValueExpression implements ValueExpression {

    private final String filedName;
    private String alias;

    public FiledValueExpression(String filedName) {
        this.filedName = filedName;
    }

    @Override
    public Object execute(BsonDocument document) {
        //TODO: It is not robust solution. It doesn't work properly for nested documents.
        // Only for the first level keys
        String[] keys = filedName.split("_");
        BsonValue value = getNestedValue(document, keys);
        if (value != null) {
            return getBsonValue(value);
        }

        return null;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FiledValueExpression that = (FiledValueExpression) o;
        return Objects.equals(filedName, that.filedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filedName);
    }


    private static BsonValue getNestedValue(BsonDocument bsonDocument, String[] keys) {
        BsonValue currentValue = bsonDocument;

        for (String key : keys) {
            if (currentValue instanceof BsonDocument document) {
                currentValue = document.get(key);
            } else {
                return null;
            }
        }

        return currentValue;
    }
}
