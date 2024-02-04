package com.numizmatclub.documentdb.parser.function;

import java.util.List;

/**
 * @author Valerii Kantor
 */
public class CountSQLFunction implements SQLFunction {

    public static final String NAME = "count";

    @Override
    public Object execute(List<Object> params) {
       return null;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
