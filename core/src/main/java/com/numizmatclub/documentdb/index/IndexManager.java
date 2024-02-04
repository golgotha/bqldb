package com.numizmatclub.documentdb.index;

import com.numizmatclub.documentdb.storage.StorageEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Valery Kantor
 */
public class IndexManager {

    private final Map<String, Index> indexes = new HashMap<>();
    private final StorageEngine storageEngine;
    private final IndexFactory indexFactory;

    public IndexManager(StorageEngine storageEngine,
                        IndexFactory indexFactory) {
        this.storageEngine = storageEngine;
        this.indexFactory = indexFactory;
    }

    public Index createIndex(String collection, String name, IndexDefinition indexDefinition) {
        IndexEngine indexEngine = indexFactory.createIndexEngine();
        IndexMetadata indexMetadata = new IndexMetadata(name, collection, indexDefinition);
        Index index = indexFactory.createIndex(storageEngine, indexEngine, indexMetadata);
        index.rebuild();
        indexes.put(index.getName(), index);
        return index;
    }

    public Stream<Index> getIndexValues() {
        return indexes.values().stream();
    }

    public Index getIndex(String key) {
        return indexes.get(key);
    }
}
