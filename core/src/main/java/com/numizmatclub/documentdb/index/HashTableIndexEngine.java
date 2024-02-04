package com.numizmatclub.documentdb.index;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

/**
 * Hash table index storage implementation.
 *
 * @author Valery Kantor
 */
public class HashTableIndexEngine implements IndexEngine {

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<IndexKey, Object> table = new LinkedHashMap<>();

    @Override
    public Object get(IndexKey key) {
        acquireSharedLock();
        try {
            return table.get(key);
        } finally {
            releaseSharedLock();
        }
    }

    @Override
    public Stream<Map.Entry<IndexKey, Object>> stream() {
        return table.entrySet().stream();
    }

    @Override
    public void put(IndexKey key, Object value) {
        acquireExclusiveLock();
        try {
            table.put(key, value);
        } finally {
            releaseExclusiveLock();
        }
    }

    @Override
    public long size() {
        return table.size();
    }

    private void acquireSharedLock() {
        lock.readLock().lock();
    }

    private void releaseSharedLock() {
        lock.readLock().unlock();
    }

    private void acquireExclusiveLock() {
        lock.writeLock().lock();
    }

    private void releaseExclusiveLock() {
        lock.writeLock().unlock();
    }
}
