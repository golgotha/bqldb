package com.numizmatclub.documentdb.parser.expression;

/**
 * @author Valerii Kantor
 */
public interface CompareOperator {

    boolean execute(Object left, Object right);

}
