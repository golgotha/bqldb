package com.numizmatclub.documentdb.index;

/**
 * @author Valery Kantor
 */
public class IndexMetadata {

    private final String name;
    private final String collection;
    private final IndexDefinition indexDefinition;

    public IndexMetadata(String name,
                         String collection,
                         IndexDefinition indexDefinition) {
        this.name = name;
        this.collection = collection;
        this.indexDefinition = indexDefinition;
    }

    public String getName() {
        return name;
    }

    public String getCollection() {
        return collection;
    }

    public IndexDefinition getIndexDefinition() {
        return indexDefinition;
    }
}
