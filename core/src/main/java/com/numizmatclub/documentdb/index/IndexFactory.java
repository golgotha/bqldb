package com.numizmatclub.documentdb.index;

import com.numizmatclub.documentdb.storage.StorageEngine;

/**
 * @author Valery Kantor
 */
public interface IndexFactory {

    Index createIndex(StorageEngine storageEngine, IndexEngine indexEngine, IndexMetadata metadata);

    IndexEngine createIndexEngine();
}
