package com.numizmatclub.documentdb.storage.result;

import com.numizmatclub.documentdb.storage.StorageDocument;

import java.util.Iterator;

public interface ResultSet extends Iterator<StorageDocument> {

    /**
     * Retrieves a value of the designated field name in the current document in ResultSet as a int.
     *
     * @param fieldName the field name in the document.
     * @return the field value.
     */
    int getInt(String fieldName);

    /**
     * Retrieves a value of the designated field name in the current document in ResultSet as a long.
     *
     * @param fieldName the field name in the document.
     * @return the field value.
     */
    long getLong(String fieldName);

    /**
     * Retrieves a value of the designated field name in the current document in ResultSet as a String.
     *
     * @param fieldName the field name in the document.
     * @return the field value.
     */
    String getString(String fieldName);

    /**
     * Gets the JSON representation of the current document in ResultSet
     * @return a JSOn representation of the document.
     */
    String toJson();

    /**
     * Get current offset of a document
     *
     * @return offset from the beginning of document.
     */
    long getOffset();

}
