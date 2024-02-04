package com.numizmatclub.documentdb.storage;

import org.bson.BsonBinaryWriter;
import org.bson.BsonDocument;
import org.bson.codecs.BsonDocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.io.BasicOutputBuffer;


public class RawDocument {

    private final long offset;

    /**
     * The size of the entire document. It may be greater than or equal to the content length.
     */
    private final int size;

    /**
     * The document content. It may contain only part of entire document.
     */
    private final byte[] content;

    public RawDocument(long offset, int size, byte[] content) {
        this.offset = offset;
        this.size = size;
        this.content = content;
    }

    public long getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }

    public byte[] getContent() {
        return content;
    }

    /**
     * Returns the offset of the next document.
     */
    public long getNextOffset() {
        return offset + size;
    }

    public static RawDocument createRawDocument(BsonDocument bsonDocument) {
        return createRawDocument(bsonDocument, null, null);
    }

    public static RawDocument createRawDocument(BsonDocument bsonDocument, Long offset, Integer size) {
        try (BasicOutputBuffer outputBuffer = new BasicOutputBuffer();
             BsonBinaryWriter writer = new BsonBinaryWriter(outputBuffer)) {

            EncoderContext encoderContext = EncoderContext.builder().build();
            new BsonDocumentCodec().encode(writer, bsonDocument, encoderContext);
            writer.flush();

            byte[] bytes = outputBuffer.toByteArray();

            offset = offset == null ? 0 : offset;
            size = size == null ? bytes.length : size;

            return new RawDocument(offset, size, bytes);
        }
    }
}
