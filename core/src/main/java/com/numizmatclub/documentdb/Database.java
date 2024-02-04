package com.numizmatclub.documentdb;


import com.numizmatclub.documentdb.storage.result.ResultSet;

import java.io.IOException;

/**
 * Generic Database inteface. Represents a low-level API.
 * @author Valery Kantor
 */
public interface Database {

    /**
     * Open a database.
     */
    Database open() throws IOException;

    ResultSet query(String query);

    Metadata getMetadata();

}
