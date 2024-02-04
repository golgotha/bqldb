package com.numizmatclub.documentdb;

import com.numizmatclub.documentdb.storage.StorageEngine;

/**
 * @author Valerii Kantor
 */
public interface DatabaseSession extends Database {

    StorageEngine getStorage();

}
