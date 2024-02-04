package com.numizmatclub.documentdb.function.evaluator;

import com.numizmatclub.documentdb.parser.expression.FunctionExpression;
import com.numizmatclub.documentdb.storage.result.ResultSet;
import com.numizmatclub.documentdb.storage.result.SingleDocumentResultSet;
import org.bson.BsonDocument;
import org.bson.Document;

public class CountFunctionEvaluator {

    private final ResultSet resultSet;
    private final FunctionExpression countFunction;

    public CountFunctionEvaluator(ResultSet resultSet, FunctionExpression countFunction) {
        this.resultSet = resultSet;
        this.countFunction = countFunction;
    }

    public ResultSet evaluate() {
        int count = 0;
        while (resultSet.hasNext()) {
            resultSet.next();
            count++;
        }

        BsonDocument countDocument = new Document()
                .append(countFunction.getAlias(), count)
                .toBsonDocument();

        return new SingleDocumentResultSet(countDocument);
    }
}
