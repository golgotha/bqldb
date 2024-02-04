package com.numizmatclub.documentdb.storage;

import com.numizmatclub.documentdb.RecordId;
import com.numizmatclub.documentdb.storage.result.ResultSet;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.RawBsonDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.stream.Stream;

import static com.numizmatclub.documentdb.ResourceUtils.getResourceAbsolutePath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultStorageEngineTest {

    private static final int COLLECTION_ID = 0;
    private DefaultStorageEngine defaultStorageEngine;

    @BeforeEach
    void init() throws IOException {
        String storagePath = getResourceAbsolutePath("data/data.bson");
        defaultStorageEngine = new DefaultStorageEngine(storagePath);
        defaultStorageEngine.open();
    }

    @Test
    void test_readOne_withCorrectOffset_successfully() {
        int offset = 96954;
        RecordId recordId = new RecordId(COLLECTION_ID, offset);
        RawDocument rawDocument = defaultStorageEngine.readOne(recordId);
        BsonDocument document = new RawBsonDocument(rawDocument.getContent());
        Document.parse(document.toJson());
        assertEquals(offset, rawDocument.getOffset());
        assertEquals(119298, rawDocument.getSize());
    }

    @Test
    void test_readOne_withIncorrectOffset_throwsException() {
        Stream.of(5, Integer.MAX_VALUE)
                .forEach(offset -> assertThrows(StorageEngineException.class, () -> defaultStorageEngine.readOne(new RecordId(COLLECTION_ID, offset))));
    }

    @Test
    void test_readOne_withNegativeOffset_returnFirstRecord() {
        RawDocument rawDocument = defaultStorageEngine.readOne(new RecordId(COLLECTION_ID, -5));
        BsonDocument document = new RawBsonDocument(rawDocument.getContent());
        String id = Document.parse(document.toJson()).get("_id").toString();
        assertEquals(0, rawDocument.getOffset());
        assertEquals("64b9b008667e313162ef50f3", id);
    }

    @Test
    void test_read_withCorrectOffset_successfully() {
        int offset = 96954;
        ResultSet resultSet = defaultStorageEngine.read(new RecordId(COLLECTION_ID, offset));

        int count = 0;
        while (resultSet.hasNext()) {
            StorageDocument document = resultSet.next();;
            Document.parse(document.getDocument().toJson());

//            assertEquals(offset, rawDocument.getOffset());

            offset += document.getSize();
            count++;
        }

        assertEquals(4, count);
    }

    @Test
    void test_read_withIncorrectOffset_throwsException() {
        Stream.of(5, Integer.MAX_VALUE)
                .forEach(offset -> assertThrows(StorageEngineException.class, () -> defaultStorageEngine.read(new RecordId(COLLECTION_ID, offset))));
    }
}
