package com.numizmatclub.documentdb.utils;

import org.bson.BsonType;
import org.bson.BsonValue;

import static com.numizmatclub.documentdb.utils.TimeUtils.convertEpochToLocalDateTime;

/**
 * @author Valerii Kantor
 */
public final class BsonValueUtils {

    private BsonValueUtils() {}

    public static Object getBsonValue(BsonValue value) {
        BsonType bsonType = value.getBsonType();
        return switch (bsonType) {
            case END_OF_DOCUMENT -> null;
            case DOUBLE -> value.asDouble().getValue();
            case STRING -> value.asString().getValue();
            case UNDEFINED -> null;
            case OBJECT_ID -> value.asObjectId().getValue();
            case BOOLEAN -> value.asBoolean().getValue();
            case DATE_TIME -> convertEpochToLocalDateTime(value.asDateTime().getValue());
            case NULL -> null;
            case INT32 -> value.asInt32().getValue();
            case TIMESTAMP -> value.asTimestamp().getValue();
            case INT64 -> value.asInt64().getValue();
            case DECIMAL128 -> value.asDecimal128().getValue().bigDecimalValue();
            default -> throw new IllegalStateException("Unsupported type: " + bsonType);
        };
    }
}
