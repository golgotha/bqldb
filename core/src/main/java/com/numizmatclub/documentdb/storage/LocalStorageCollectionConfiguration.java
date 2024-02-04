package com.numizmatclub.documentdb.storage;

/**
 * @author Valerii Kantor
 */
public class LocalStorageCollectionConfiguration implements StorageCollectionConfiguration {

    private final String location;

    public LocalStorageCollectionConfiguration(String location) {
        this.location = location;
    }


    @Override
    public String getLocation() {
        return location;
    }
}
