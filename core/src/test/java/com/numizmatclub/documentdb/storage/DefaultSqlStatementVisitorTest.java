package com.numizmatclub.documentdb.storage;

import com.numizmatclub.documentdb.Database;
import com.numizmatclub.documentdb.parser.jsql.JSqlQueryParser;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

/**
 * @author Valerii Kantor
 */
class DefaultSqlStatementVisitorTest {

    private final JSqlQueryParser queryParser = new JSqlQueryParser();
    private Database database = mock(Database.class);
    private StorageEngine storageEngine = mock(StorageEngine.class);

    @BeforeEach
    void setUp() {
        reset(storageEngine);
        reset(database);
    }

    /*@Test
    void test_parse_selectQueryWithComplexOperationInWhere_successfully() {
        String query = "SELECT * FROM data WHERE id = 5 AND (data < 5 OR data > 10)";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        String storagePath = getResourceAbsolutePath("data/data.bson");

        final StorageCollection storageCollection = new SimpleFileCollection("data", 0);
        storageCollection.configure(new LocalStorageCollectionConfiguration(storagePath));

        when(storageEngine.getCollection("data")).thenReturn(storageCollection);
        DefaultMetadata metadata = new DefaultMetadata(storageEngine, new HashTableIndexFactory());
        when(database.getMetadata()).thenReturn(metadata);
        assertEquals("data", statement.getFrom());
        assertNull(statement.getLimit());
        assertNull(statement.getOffset());
        DefaultSqlStatementVisitor sqlStatementVisitor = new DefaultSqlStatementVisitor(database, storageEngine);
        sqlStatementVisitor.visit(statement);

        assertNotNull(statement.getWhere().getWhereExpression());
        assertTrue(statement.getProjection().isEmpty());
    }*/
}
