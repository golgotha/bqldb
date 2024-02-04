package com.numizmatclub.documentdb.parser.expression;

import com.numizmatclub.documentdb.storage.DocumentMatcher;

/**
 * @author Valerii Kantor
 */
public interface BooleanExpressionVisitor {

    DocumentMatcher visit(AndConditionExpression expression);

    DocumentMatcher visit(OrConditionExpression expression);

    DocumentMatcher visit(InConditionExpression expression);

    DocumentMatcher visit(BinaryConditionExpression expression);

    DocumentMatcher visit(BetweenExpression expression);

}
