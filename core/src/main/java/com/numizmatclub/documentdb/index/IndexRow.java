package com.numizmatclub.documentdb.index;

import java.util.List;
import java.util.Objects;

/**
 * It is a comparable object with the required data for index value creation. A comparing result
 * depends on index field ordering. If the order is {@link Order#DEFAULT}, then comparing will be
 * made by the position of a document.
 */
public class IndexRow implements Comparable<IndexRow> {

    private final List<IndexField> indexFields;
    private final List<? extends Comparable> params;
    private final long offset;

    public IndexRow(List<IndexField> indexFields, List<? extends Comparable> params, long offset) {
        this.indexFields = indexFields;
        this.params = params;
        this.offset = offset;
    }

    public List<IndexField> getIndexFields() {
        return indexFields;
    }

    public List<? extends Comparable> getParams() {
        return params;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexRow indexRow = (IndexRow) o;
        return offset == indexRow.offset && Objects.equals(indexFields, indexRow.indexFields) && Objects.equals(params, indexRow.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexFields, params, offset);
    }

    @Override
    public int compareTo(IndexRow o) {
        for (int i = 0; i < params.size(); i++) {
            Comparable<Object> object1 = params.get(i);
            Comparable<Object> object2 = o.params.get(i);

            Order order = indexFields.get(i).order();
            if (order == Order.DEFAULT) {
                return Long.compare(offset, o.offset);
            }

            if (object1 == null && object2 == null) {
                continue;
            }

            if (object1 == null) {
                return -1;
            }

            if (object2 == null) {
                return 1;
            }

            int compare = order == Order.ASC
                    ? object1.compareTo(object2)
                    : object2.compareTo(object1);

            if (compare != 0) {
                return compare;
            }
        }
        return Long.compare(offset, o.offset);
    }
}
