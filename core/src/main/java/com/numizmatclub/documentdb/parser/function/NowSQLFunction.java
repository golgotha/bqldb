package com.numizmatclub.documentdb.parser.function;

import java.time.LocalTime;
import java.util.List;

/**
 * @author Valerii Kantor
 */
public class NowSQLFunction implements SQLFunction {
    public static final String NAME = "now";

    @Override
    public Object execute(List<Object> params) {
        return LocalTime.now();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
