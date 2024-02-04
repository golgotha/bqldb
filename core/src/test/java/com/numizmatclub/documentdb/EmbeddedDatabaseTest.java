package com.numizmatclub.documentdb;

import com.numizmatclub.documentdb.storage.result.ResultSet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Valerii Kantor
 */
class EmbeddedDatabaseTest {

    private final EmbeddedDatabase embeddedDatabase;

    public EmbeddedDatabaseTest() throws IOException {
        this.embeddedDatabase = new EmbeddedDatabase("./target");
        this.embeddedDatabase.open();
    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    void shouldCreateCollection() throws IOException {
        String query = """
                CREATE TABLE test;
                """;
        embeddedDatabase.query(query);
        ResultSet resultSet = embeddedDatabase.query("SELECT * FROM test");
        assertFalse(resultSet.hasNext());
    }
}
