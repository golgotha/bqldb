package com.numizmatclub.documentdb.index;

import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Valery Kantor
 */
public interface IndexEngine {

    /**
     * Get a index entry from index storage.
     *
     * @param key An index key.
     * @return The index entry.
     */
    Object get(IndexKey key);

    Stream<Map.Entry<IndexKey, Object>> stream();

    /**
     * Puts index value to index storage.
     *
     * @param key An index key associated with the value.
     * @param value A index value.
     */
    void put(IndexKey key, Object value);

    /**
     * Returns size of index.
     */
    long size();

}
