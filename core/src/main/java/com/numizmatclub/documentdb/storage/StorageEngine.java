package com.numizmatclub.documentdb.storage;

import com.numizmatclub.documentdb.RecordId;
import com.numizmatclub.documentdb.storage.result.ResultSet;

import java.io.IOException;
import java.util.Set;

public interface StorageEngine {

    void open() throws IOException;

    /**
     * Create a new collection.
     *
     * @param name A collection name.
     */
    void createCollection(String name);

    Set<String> getCollectionNames();

    StorageCollection getCollection(String collectionName);

    /**
     * Read the single document in binary format with the specified offset.
     *
     * @param recordId record ID.
     * @return the document in binary format
     */
    RawDocument readOne(RecordId recordId);

    /**
     * Read the documents in binary format with the specified offset.
     * The result is represented by an iterable {@link ResultSet}.
     *
     * @param recordId record ID.
     * @return an iterable result set
     */
    ResultSet read(RecordId recordId);


}
