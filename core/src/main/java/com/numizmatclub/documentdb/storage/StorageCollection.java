package com.numizmatclub.documentdb.storage;

import com.numizmatclub.documentdb.storage.result.ResultSet;

/**
 * @author Valerii Kantor
 */
public interface StorageCollection {

    String COLLECTION_EXTENSION = ".bson";

    /**
     *
     * @return Collection name.
     */
    String getName();

    int getId();

    void configure(StorageCollectionConfiguration configuration);

    /**
     * Read the single document in binary format with the specified offset.
     *
     * @param offset offset
     * @return the document in binary format
     */
    RawDocument readOne(long offset);

    /**
     * Read the documents in binary format with the specified offset.
     * The result is represented by an iterable {@link ResultSet}.
     *
     * @param offset offset
     * @return an iterable result set
     */
    ResultSet read(long offset);

}
