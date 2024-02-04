package com.numizmatclub.documentdb.index;

import java.util.Objects;

/**
 * @author Valery Kantor
 */
public class PropertyIndexKey implements IndexKey, Comparable<PropertyIndexKey> {

    private final Object param;

    public PropertyIndexKey(Object param) {
        this.param = param;
    }

    @Override
    public Object getParam() {
        return param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyIndexKey that = (PropertyIndexKey) o;
        return Objects.equals(param, that.param);
    }

    @Override
    public int hashCode() {
        return Objects.hash(param);
    }

    @Override
    public int compareTo(PropertyIndexKey o) {
        return o.compareTo(this);
    }
}
