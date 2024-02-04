package com.numizmatclub.documentdb.storage.result;

import com.numizmatclub.documentdb.storage.StorageDocument;
import org.bson.BsonDocument;

import java.util.NoSuchElementException;

public class SingleDocumentResultSet extends AbstractDocumentResultSet {

    private final BsonDocument current;
    private BsonDocument next;

    public SingleDocumentResultSet(BsonDocument current) {
        this.current = current;
        this.next = current;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public StorageDocument next() {
        if (next == null) {
            throw new NoSuchElementException();
        }

        BsonDocument result = next;
        next = null;
        return new StorageDocument(result, -1, -1);
    }

    @Override
    public long getOffset() {
        throw new UnsupportedOperationException("getOffset unsupported for single document");
    }

    @Override
    protected BsonDocument getDocument() {
        return current;
    }
}
