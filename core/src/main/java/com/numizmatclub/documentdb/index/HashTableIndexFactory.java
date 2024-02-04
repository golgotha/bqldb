package com.numizmatclub.documentdb.index;

import com.numizmatclub.documentdb.storage.StorageEngine;

/**
 * @author Valery Kantor
 */
public class HashTableIndexFactory implements IndexFactory {

    @Override
    public Index createIndex(StorageEngine storageEngine, IndexEngine indexEngine, IndexMetadata metadata) {
        return new MultiValuesIndex(storageEngine, metadata, indexEngine);
    }

    @Override
    public IndexEngine createIndexEngine() {
        return new HashTableIndexEngine();
    }
}
