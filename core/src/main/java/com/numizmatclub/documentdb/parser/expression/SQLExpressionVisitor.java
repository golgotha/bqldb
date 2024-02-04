package com.numizmatclub.documentdb.parser.expression;

import com.numizmatclub.documentdb.storage.DocumentMatcher;

/**
 * @author Valerii Kantor
 */
public interface SQLExpressionVisitor {

    DocumentMatcher visit(FunctionExpression expression);
}
