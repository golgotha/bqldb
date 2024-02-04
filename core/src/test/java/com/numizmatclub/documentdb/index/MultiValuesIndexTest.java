package com.numizmatclub.documentdb.index;

import com.numizmatclub.documentdb.RecordId;
import com.numizmatclub.documentdb.storage.DefaultStorageEngine;
import com.numizmatclub.documentdb.storage.StorageEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.numizmatclub.documentdb.ResourceUtils.getResourceAbsolutePath;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Valery Kantor
 */
class MultiValuesIndexTest {
    private static  final String INDEX_NAME = "test_idx";
    private static  final String COLLECTION_NAME = "data";

    private StorageEngine storageEngine;
    private IndexEngine indexEngine;
    private IndexMetadata indexMetadata;

    @BeforeEach
    void setUp() {
        storageEngine = mock(StorageEngine.class);
        indexEngine = spy(new HashTableIndexEngine());
        PropertyIndexDefinition indexDefinition = new PropertyIndexDefinition(new IndexField("eventId", Order.DEFAULT));
        this.indexMetadata = new IndexMetadata(INDEX_NAME, COLLECTION_NAME, indexDefinition);
    }

    @Test
    void test_getName() {
        MultiValuesIndex index = new MultiValuesIndex(storageEngine, indexMetadata, indexEngine);
        String actualName = index.getName();
        assertEquals(INDEX_NAME, actualName);
    }

    @Test
    void test_putValueToIndex() {
        MultiValuesIndex index = new MultiValuesIndex(storageEngine, indexMetadata, indexEngine);
        IndexKey indexKey = index.getDefinition().createIndexKey(List.of("sr:match:1"));

        index.put(indexKey, new RecordId(1, 1));
        index.put(indexKey, new RecordId(1, 2));
        index.put(indexKey, new RecordId(1, 3));

        verify(indexEngine, times(3)).put(eq(indexKey), any(Object.class));

        Collection<RecordId> actualValues = index.get(indexKey);
        assertEquals(3, actualValues.size());

        List<RecordId> actualRecords = new ArrayList<>(actualValues);
        assertEquals(1, actualRecords.get(0).getCurrentPosition());
        assertEquals(2, actualRecords.get(1).getCurrentPosition());
        assertEquals(3, actualRecords.get(2).getCurrentPosition());
    }

    @Test
    void test_get_index_when_no_value_exists() {
        MultiValuesIndex index = new MultiValuesIndex(storageEngine, indexMetadata, indexEngine);
        IndexKey indexKey = index.getDefinition().createIndexKey(List.of("sr:match:1"));
        Collection<RecordId> actualRecordIds = index.get(indexKey);
        assertNull(actualRecordIds);
    }

    @Test
    void test_remove() {
        MultiValuesIndex index = new MultiValuesIndex(storageEngine, indexMetadata, indexEngine);
        IndexKey indexKey = new PropertyIndexKey("sr:match:1");
        assertThrows(UnsupportedOperationException.class, () -> index.remove(indexKey));
    }

    @Test
    void test_rebuild() throws IOException {
        String filePath = getResourceAbsolutePath("data/data.bson");
        StorageEngine storageEngine = spy(new DefaultStorageEngine(filePath));
        storageEngine.open();

        MultiValuesIndex index = new MultiValuesIndex(storageEngine, indexMetadata, indexEngine);
        long numberEntries = index.rebuild();
        assertEquals(5, numberEntries);
    }
}
