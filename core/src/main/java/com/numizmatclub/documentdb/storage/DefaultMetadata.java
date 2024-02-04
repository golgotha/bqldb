package com.numizmatclub.documentdb.storage;

import com.numizmatclub.documentdb.Metadata;
import com.numizmatclub.documentdb.index.IndexFactory;
import com.numizmatclub.documentdb.index.IndexManager;

public class DefaultMetadata implements Metadata {

    private final IndexManager indexManager;

    public DefaultMetadata(StorageEngine storageEngine, IndexFactory indexFactory) {
        this.indexManager = new IndexManager(storageEngine, indexFactory);
    }

    @Override
    public IndexManager getIndexManager() {
        return indexManager;
    }
}
