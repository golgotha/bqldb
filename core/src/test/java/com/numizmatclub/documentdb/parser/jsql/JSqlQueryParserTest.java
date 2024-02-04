package com.numizmatclub.documentdb.parser.jsql;

import com.numizmatclub.documentdb.index.IndexField;
import com.numizmatclub.documentdb.index.Order;
import com.numizmatclub.documentdb.parser.CreateIndexStatement;
import com.numizmatclub.documentdb.parser.SelectStatement;
import com.numizmatclub.documentdb.parser.SqlParserException;
import com.numizmatclub.documentdb.parser.expression.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JSqlQueryParserTest {

    private final JSqlQueryParser queryParser = new JSqlQueryParser();

    @Test
    void test_parse_simpleSelectQuery_successfully() {
        String query = "SELECT * FROM a";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        assertEquals("a", statement.getFrom());
        assertNull(statement.getLimit());
        assertNull(statement.getOffset());
        assertNull(statement.getWhere());
        assertTrue(statement.getProjection().isEmpty());
    }


    @Test
    void test_parse_SelectQueryWithNestedDocumentInWhereClause_successfully() {
        String query = "SELECT * FROM a WHERE event.id = 5";

        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        assertEquals("a", statement.getFrom());
        assertNull(statement.getLimit());
        assertNull(statement.getOffset());
        assertNotNull(statement.getWhere().getWhereExpression());
        assertTrue(statement.getProjection().isEmpty());

    }

    @Test
    void test_parse_selectQueryWithProjection_successfully() {
        String query = "SELECT id, data FROM a";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        assertEquals("a", statement.getFrom());
        assertNull(statement.getLimit());
        assertNull(statement.getOffset());
        assertNull(statement.getWhere());

        //TODO: Fix this test
        List<ValueExpression> expectedProjection = List.of(
                new FiledValueExpression("id"),
                new FiledValueExpression("data"));

        assertEquals(expectedProjection, statement.getProjection());
    }

    @Test
    void test_parse_selectQueryWithLimit_successfully() {
        String query = "SELECT * FROM a LIMIT 10";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        assertEquals("a", statement.getFrom());
        assertEquals(10, statement.getLimit());
        assertNull(statement.getOffset());
        assertNull(statement.getWhere());
        assertTrue(statement.getProjection().isEmpty());
    }

    @Test
    void test_parse_selectQueryWithOffset_successfully() {
        String query = "SELECT * FROM a OFFSET 10";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        assertEquals("a", statement.getFrom());
        assertNull(statement.getLimit());
        assertEquals(10, statement.getOffset());
        assertNull(statement.getWhere());
        assertTrue(statement.getProjection().isEmpty());
    }

    @Test
    void test_parse_selectQueryWithEqualsOperationInWhere_successfully() {
        String query = "SELECT * FROM a WHERE id = 5";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        assertEquals("a", statement.getFrom());
        assertNull(statement.getLimit());
        assertNull(statement.getOffset());

        assertNotNull(statement.getWhere().getWhereExpression());
        assertTrue(statement.getProjection().isEmpty());
    }

    @Test
    void test_parse_selectQueryWithComparisonOperationInWhere_successfully() {
        String queryTemplate = "SELECT * FROM a WHERE id %s 5";

        List<String> operators = new ArrayList<>();
        operators.add("=");
        operators.add(">");
        operators.add("<");
        operators.add(">=");
        operators.add("<=");

        operators.forEach((operator) -> {
            String query = String.format(queryTemplate, operator);
            SelectStatement statement = (SelectStatement) queryParser.parse(query);

            assertEquals("a", statement.getFrom());
            assertNull(statement.getLimit());
            assertNull(statement.getOffset());

            assertNotNull(statement.getWhere().getWhereExpression());
            assertTrue(statement.getProjection().isEmpty());
        });
    }

    @Test
    void test_parse_selectQueryWithInOperationInWhere_successfully() {
        String query = "SELECT * FROM a WHERE id IN (1, 2, 3, 5)";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        assertEquals("a", statement.getFrom());
        assertNull(statement.getLimit());
        assertNull(statement.getOffset());
        assertNotNull(statement.getWhere().getWhereExpression());
        assertTrue(statement.getProjection().isEmpty());
    }

    @Test
    void test_parse_selectQueryWithBetweenOperationInWhere_successfully() {
        String query = "SELECT * FROM a WHERE id BETWEEN 5 AND 10";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        assertEquals("a", statement.getFrom());
        assertNull(statement.getLimit());
        assertNull(statement.getOffset());

        assertNotNull(statement.getWhere().getWhereExpression());
        assertTrue(statement.getProjection().isEmpty());
    }

    @Test
    void test_parse_selectQueryWithComplexOperationInWhere_successfully() {
        String query = "SELECT * FROM a WHERE id = 5 AND (data < 5 OR data > 10)";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        assertEquals("a", statement.getFrom());
        assertNull(statement.getLimit());
        assertNull(statement.getOffset());

        assertNotNull(statement.getWhere().getWhereExpression());
        assertTrue(statement.getProjection().isEmpty());
    }

    @Test
    void test_parse_selectQueryWithTimeFunctionInLeftExpression_successfully() {
        String query = "SELECT * FROM a WHERE TIME(timestamp) > '15:00'";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        BooleanExpression actualExpression = statement.getWhere().getWhereExpression();
        assertNotNull(actualExpression);

        assertEquals("a", statement.getFrom());
        assertInstanceOf(BinaryConditionExpression.class, statement.getWhere().getWhereExpression());

        assertNull(statement.getLimit());
        assertNull(statement.getOffset());
    }

    @Test
    void test_parse_simpleSelectQuery_with_COUNT_function_successfully() {
        String query = "SELECT COUNT(*) FROM a";
        SelectStatement statement = (SelectStatement) queryParser.parse(query);

        assertEquals("a", statement.getFrom());
        assertNull(statement.getLimit());
        assertNull(statement.getOffset());
        assertNull(statement.getWhere());
        ValueExpression actualExpression = statement.getProjection().get(0);
        assertInstanceOf(FunctionExpression.class, actualExpression);
        assertEquals("count", ((FunctionExpression)actualExpression).getFunctionName());
    }

    @Test
    void test_parse_incorrectSelectQuery_throwsException() {
        String query = "incorrect query";
        assertThrows(SqlParserException.class, () -> queryParser.parse(query));
    }

    @Test
    void test_create_index_query_successfully() {
        String query = "CREATE INDEX event_id_idx ON stub_data (eventId)";
        CreateIndexStatement statement = (CreateIndexStatement) queryParser.parse(query);
        assertNotNull(statement);
        assertEquals("event_id_idx", statement.getName());
        assertEquals(new IndexField("eventId", Order.DEFAULT), statement.getFields().get(0));
    }

    @Test
    void test_create_index_query_throwsException_when_not_valid_query() {
        String query = "CREATE INDEX event_id_idx ON stub_data eventId";
        assertThrows(SqlParserException.class, () -> queryParser.parse(query));
    }
}
