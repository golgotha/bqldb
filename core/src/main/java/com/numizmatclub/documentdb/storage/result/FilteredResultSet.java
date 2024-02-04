package com.numizmatclub.documentdb.storage.result;

import com.numizmatclub.documentdb.parser.expression.ValueExpression;
import com.numizmatclub.documentdb.storage.*;
import com.numizmatclub.documentdb.storage.result.AbstractDocumentResultSet;
import com.numizmatclub.documentdb.storage.result.ResultSet;
import org.bson.BsonDocument;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The result set is filtered by a provided document matcher. The order of result documents
 * depends on the document matcher.
 */
public class FilteredResultSet extends AbstractDocumentResultSet {

    private final StorageEngine storageEngine;
    private final DocumentMatcher documentMatcher;
    private final String collection;
    private final List<ValueExpression> projection;
    private final long offset;
    private Long limit;
    private long documentOffset = 0;

    private StorageDocument currentDocument;
    private StorageDocument nextDocument;

    public FilteredResultSet(StorageEngine storageEngine,
                             DocumentMatcher documentMatcher,
                             String collection,
                             List<ValueExpression> projection,
                             Long offset,
                             Long limit) {
        this.storageEngine = storageEngine;
        this.offset = offset == null ? 0 : offset;
        this.limit = limit;
        this.projection = projection;
        this.collection = collection;
        this.documentMatcher = documentMatcher;
        this.currentDocument = extractFirstDocument();
        this.nextDocument = currentDocument;
    }

    private StorageDocument extractFirstDocument() {
        if (limit != null && limit == 0) {
            return null;
        }

        long positionOffset = 0;
        StorageDocument document = null;
        // Skip documents preceding the offset value
        for (long i = 0; i <= offset; i++) {
            document = extractNext(positionOffset);
            if (document == null) {
                return null;
            }

            positionOffset = document.getPosition() + document.getSize();
        }
        return document;
    }

    @Override
    public boolean hasNext() {
        return nextDocument != null;
    }

    @Override
    public StorageDocument next() {
        if (nextDocument == null) {
            throw new NoSuchElementException();
        }

        currentDocument = nextDocument;
        if (limit != null) {
            limit--;
            if (limit == 0) {
                nextDocument = null;
                return currentDocument;
            }
        }

        this.documentOffset = currentDocument.getNextDocumentOffset();
        nextDocument = extractNext(this.documentOffset);

        return currentDocument;
    }

    private StorageDocument extractNext(long offset) {
        try {
            StorageCollection storageCollection = storageEngine.getCollection(collection);
            ResultSet sequentialResultSet = storageCollection.read(offset);
            while (sequentialResultSet.hasNext()) {
                StorageDocument storageDocument = sequentialResultSet.next();
                BsonDocument bsonDocument = storageDocument.getDocument();
                if (documentMatcher.matches(bsonDocument)) {
                    // TODO: move projection as the last query execution step
//                        if (!projection.isEmpty()) {
//                            bsonDocument = createProjection(bsonDocument);
//                        }
                    return new StorageDocument(bsonDocument, storageDocument.getPosition(), storageDocument.getSize());
                }
            }

            return null;
        } catch (OffsetExceededException e) {
            return null;
        } catch (StorageEngineException e) {
            throw e;
        } catch (Exception e) {
            throw new StorageEngineException("Can not extract result", e);
        }
    }

    @Override
    protected BsonDocument getDocument() {
        return currentDocument.getDocument();
    }

    @Override
    public long getOffset() {
        return documentOffset;
    }
}
