package com.numizmatclub.documentdb.storage;

/**
 * @author Valerii Kantor
 */
public final class CollectionFactory {

    private CollectionFactory() {
    }

    public static StorageCollection createCollection(String name, int collectionId) {
        return new SimpleFileCollection(name, collectionId);
    }
}
