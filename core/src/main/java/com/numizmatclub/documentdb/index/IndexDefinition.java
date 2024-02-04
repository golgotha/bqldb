package com.numizmatclub.documentdb.index;

import java.util.List;

/**
 * @author Valery Kantor
 */
public interface IndexDefinition {

    List<IndexField> getFields();

    IndexKey createIndexKey(List<?> params);

}
