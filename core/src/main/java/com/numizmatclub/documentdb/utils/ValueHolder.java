package com.numizmatclub.documentdb.utils;

/**
 * @author Valery Kantor
 */
public class ValueHolder<V> {

    private V value;

    public ValueHolder() {
    }

    public ValueHolder(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
