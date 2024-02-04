package com.numizmatclub.documentdb;

import com.numizmatclub.documentdb.index.HashTableIndexFactory;
import com.numizmatclub.documentdb.index.IndexFactory;
import com.numizmatclub.documentdb.parser.QueryParser;
import com.numizmatclub.documentdb.parser.SqlStatement;
import com.numizmatclub.documentdb.parser.jsql.JSqlQueryParser;
import com.numizmatclub.documentdb.storage.DefaultMetadata;
import com.numizmatclub.documentdb.storage.DefaultStorageEngine;
import com.numizmatclub.documentdb.storage.StorageEngine;
import com.numizmatclub.documentdb.storage.result.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class EmbeddedDatabase implements DatabaseSession {

    private static final Logger log = LoggerFactory.getLogger(EmbeddedDatabase.class);

    private final StorageEngine storageEngine;
    private final IndexFactory indexFactory;
    private final QueryParser queryParser;
    private Metadata metadata;

    public EmbeddedDatabase(String path) {
        this.storageEngine = new DefaultStorageEngine(path);
        this.indexFactory = new HashTableIndexFactory();
        this.queryParser = new JSqlQueryParser();
    }

    @Override
    public Database open() throws IOException {
        log.info("Open database. Create metadata and indexes.");
        storageEngine.open();
        metadata = new DefaultMetadata(storageEngine, indexFactory);
        return this;
    }

    @Override
    public ResultSet query(String query) {
        if (log.isDebugEnabled()) {
            log.debug(query);
        }

        SqlStatement statement = queryParser.parse(query);
        return statement.execute(this);
    }

    @Override
    public Metadata getMetadata() {
        return metadata;
    }

    @Override
    public StorageEngine getStorage() {
        return storageEngine;
    }
}
