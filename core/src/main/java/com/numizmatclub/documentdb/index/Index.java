package com.numizmatclub.documentdb.index;

import com.numizmatclub.documentdb.RecordId;

import java.util.Map;
import java.util.stream.Stream;

/**
 * An interface for index handling.
 *
 * @author Valery Kantor
 */
public interface Index extends Comparable<Index> {

    /**
     * Returns the index name.
     *
     * @return The name of the index.
     */
     String getName();

    /**
     * Get set of entries associated with the key.
     *
     * @param key The key to search.
     * @return The set of records.
     */
     Object get(IndexKey key);

     Stream<Map.Entry<IndexKey, Object>> stream();

    /**
     * Inserts a new entry in the index.
     *
     * @param key Entry's key
     * @param value Entry's value.
     * @return
     */
     Index put(IndexKey key, RecordId value);

    /**
     * Removes an entry from the index by key.
     *
     * @param key Entry's key to remove.
     * @return Returns true if the entry has been removed, otherwise false.
     */
     boolean remove(IndexKey key);

    /**
     * Rebuilds an index.
     *
     * @return Number of entries
     */
    long rebuild();

    IndexDefinition getDefinition();
}
