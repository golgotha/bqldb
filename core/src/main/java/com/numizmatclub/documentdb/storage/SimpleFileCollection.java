package com.numizmatclub.documentdb.storage;

import com.numizmatclub.documentdb.storage.result.AbstractDocumentResultSet;
import com.numizmatclub.documentdb.storage.result.ResultSet;
import org.bson.BsonDocument;
import org.bson.RawBsonDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;
import java.util.NoSuchElementException;

/**
 * @author Valerii Kantor
 */
public class SimpleFileCollection implements StorageCollection {

    private static final Logger log = LoggerFactory.getLogger(SimpleFileCollection.class);

    private final String name;
    private final String fullName;
    private int id;
    private File collectionFile;

    public SimpleFileCollection(String name, int collectionId) {
        this.name = name;
        this.id = collectionId;
        this.fullName = name + COLLECTION_EXTENSION;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void configure(StorageCollectionConfiguration configuration) {
        String location = configuration.getLocation();
        this.collectionFile = Path.of(location, this.fullName).toFile();
    }

    @Override
    public RawDocument readOne(long offset) {
        return readDocument(offset);
    }

    @Override
    public ResultSet read(long offset) {
        return new DefaultResultSet(readDocument(offset));
    }

    private RawDocument readDocument(long offset) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(this.collectionFile, "r")) {
            offset = Math.max(offset, 0);

            if (offset >= randomAccessFile.length()) {
                throw new OffsetExceededException("Offset was exceeded");
            }

            int documentSize = getDocumentSize(offset, randomAccessFile);
            byte[] documentBytes = new byte[documentSize];
            randomAccessFile.seek(offset);
            randomAccessFile.readFully(documentBytes);
            return new RawDocument(offset, documentSize, documentBytes);
        } catch (OffsetExceededException e) {
            throw e;
        } catch (Exception e) {
            throw new StorageEngineException(e.getMessage(), e);
        }
    }

    private int getDocumentSize(long offset, RandomAccessFile randomAccessFile) throws IOException {
        randomAccessFile.seek(offset);
        byte[] sizeBytes = new byte[4];
        randomAccessFile.readFully(sizeBytes);
        ByteBuffer buffer = ByteBuffer.wrap(sizeBytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        return buffer.getInt();
    }

    public class DefaultResultSet extends AbstractDocumentResultSet {

        private RawDocument currentDocument;
        private RawDocument nextDocument;

        public DefaultResultSet(RawDocument current) {
            this.currentDocument = current;
            this.nextDocument = current;
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
            try {
                nextDocument = readDocument(currentDocument.getNextOffset());
            } catch (OffsetExceededException e) {
                nextDocument = null;
            }

            RawBsonDocument rawBsonDocument = new RawBsonDocument(currentDocument.getContent());
            if (log.isTraceEnabled()) {
                log.trace("""
                         {
                            Document offset: {},
                            Document size: {},
                            Document content: {}
                         }
                        """, currentDocument.getOffset(), currentDocument.getSize(), rawBsonDocument.toJson()
                );
            }

            return new StorageDocument(rawBsonDocument, currentDocument.getOffset(), currentDocument.getSize());
        }

        @Override
        protected BsonDocument getDocument() {
            return new RawBsonDocument(currentDocument.getContent());
        }

        @Override
        public long getOffset() {
            return currentDocument.getOffset();
        }
    }
}
