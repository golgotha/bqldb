package com.numizmatclub.documentdb.index;

import com.numizmatclub.documentdb.RecordId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class HashTableIndexEngineTest {

    @Test
    void testPutIndexWithPropertyKey() {
        PropertyIndexDefinition indexDefinition = new PropertyIndexDefinition(new IndexField("eventId", Order.DEFAULT));
        IndexKey indexKey = indexDefinition.createIndexKey(List.of("sr:match:1"));
        HashTableIndexEngine indexEngine = new HashTableIndexEngine();
        indexEngine.put(indexKey, new RecordId(1, 1));

        IndexKey lookupIndexKey = indexDefinition.createIndexKey(List.of("sr:match:1"));
        RecordId actualValue = (RecordId) indexEngine.get(lookupIndexKey);

        assertNotNull(actualValue);
        assertEquals(1, actualValue.getCurrentPosition());
    }

    @Test
    void testPutIndexWithMultiValues() {
        PropertyIndexDefinition indexDefinition = new PropertyIndexDefinition(new IndexField("eventId", Order.DEFAULT));
        IndexKey indexKey1 = indexDefinition.createIndexKey(List.of("sr:match:1"));
        IndexKey indexKey2 = indexDefinition.createIndexKey(List.of("sr:match:2"));

        HashTableIndexEngine indexEngine = new HashTableIndexEngine();
        List<RecordId> indexValues = List.of(new RecordId(1, 1), new RecordId(1, 2));
        indexEngine.put(indexKey1, indexValues);
        indexEngine.put(indexKey2, List.of(new RecordId(1, 3)));

        IndexKey lookupIndexKey = indexDefinition.createIndexKey(List.of("sr:match:1"));
        Object actualValue = indexEngine.get(lookupIndexKey);

        assertNotNull(actualValue);
        assertTrue(actualValue instanceof List);
        assertEquals(2, ((List<RecordId>) actualValue).size());

        Object actualValue2 = indexEngine.get(indexDefinition.createIndexKey(List.of("sr:match:2")));
        assertNotNull(actualValue2);
        assertTrue(actualValue2 instanceof List);
        assertEquals(1, ((List<RecordId>) actualValue2).size());
    }

}
