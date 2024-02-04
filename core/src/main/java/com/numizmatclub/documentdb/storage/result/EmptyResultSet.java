package com.numizmatclub.documentdb.storage.result;

import com.numizmatclub.documentdb.storage.StorageDocument;

/**
 * @author Valerii Kantor
 */
public class EmptyResultSet implements ResultSet {
    @Override
    public int getInt(String fieldName) {
        return 0;
    }

    @Override
    public long getLong(String fieldName) {
        return 0;
    }

    @Override
    public String getString(String fieldName) {
        return null;
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public long getOffset() {
        return 0;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public StorageDocument next() {
        return null;
    }
}
