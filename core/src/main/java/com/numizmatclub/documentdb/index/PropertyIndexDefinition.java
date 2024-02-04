package com.numizmatclub.documentdb.index;

import java.util.Collections;
import java.util.List;

/**
 * @author Valery Kantor
 */
public class PropertyIndexDefinition implements IndexDefinition {

    private final IndexField indexField;

    public PropertyIndexDefinition(IndexField indexField) {
        this.indexField = indexField;
    }


    @Override
    public List<IndexField> getFields() {
        return Collections.singletonList(indexField);
    }

    @Override
    public IndexKey createIndexKey(List<?> params) {
        return new PropertyIndexKey(params.get(0));
    }
}
