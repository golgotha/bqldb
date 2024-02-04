package com.numizmatclub.documentdb.parser.function;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Valerii Kantor
 */
public class SQLFunctions {

    private static final List<SQLFunction> supportedFunctions = new ArrayList<>();

    static {
        supportedFunctions.add(new TimeSQLFunction());
        supportedFunctions.add(new NowSQLFunction());
        supportedFunctions.add(new CountSQLFunction());
    }

    public static SQLFunction getFunction(String name) {
        for (SQLFunction supportedFunction : supportedFunctions) {
            if (supportedFunction.getName().equals(name)) {
                return supportedFunction;
            }
        }
        throw new IllegalArgumentException("Function " + name + " unsupported by database");
    }
}
