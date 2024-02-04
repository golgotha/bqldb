package com.numizmatclub.documentdb.storage;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonType;
import org.bson.BsonValue;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static com.numizmatclub.documentdb.utils.TimeUtils.convertEpochToLocalDateTime;

/**
 * @author Valerii Kantor
 */
public class StorageDocument {

    private final BsonDocument document;
    private final long position;
    private final int size;

    public StorageDocument(BsonDocument document, long position, int size) {
        this.document = document;
        this.position = position;
        this.size = size;
    }

    public BsonDocument getDocument() {
        return document;
    }

    public long getPosition() {
        return position;
    }

    public int getSize() {
        return size;
    }

    public long getNextDocumentOffset() {
        return getPosition() + getSize();
    }

    public Comparable<?> asObject(BsonValue value) {
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


    /**
     * Travers document to look up a value for given a key.
     *
     * @param key A key for lookup
     * @return A BsonValue
     */
    public BsonValue traverse(String key) {
        Queue<String> keys = new ArrayDeque<>(List.of(key.split("\\.")));
        return traverseDocument(document, keys);
    }

    private BsonValue traverseDocument(BsonDocument document, Queue<String> keys) {
        BsonValue value = document.get(keys.poll());
        if (keys.isEmpty()) {
            return value;
        }

        if (value.isDocument()) {
           return traverseDocument(value.asDocument(), keys);
        } else if (value.isArray()) {
            return traverseArray(value.asArray(), keys);
        } else {
            return value;
        }
    }
    private BsonValue traverseArray(BsonArray array, Queue<String> keys) {
        if (keys.isEmpty()) {
            return array;
        }

        for (BsonValue bsonValue : array) {
            if (bsonValue.isDocument()) {
                return traverseDocument(bsonValue.asDocument(), keys);
            } else if (bsonValue.isArray()) {
                return traverseArray(bsonValue.asArray(), keys);
            } else {
                return bsonValue;
            }
        }

        return array;
    }
}
