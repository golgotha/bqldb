package com.numizmatclub.documentdb.parser.function;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * @author Valerii Kantor
 */
public class TimeSQLFunction implements SQLFunction {

    public static final String NAME = "time";

    @Override
    public Object execute(List<Object> params) {
        if (params.isEmpty()) return LocalTime.now();

        if (params.get(0) == null) {
            return null;
        }
        Object parameter = params.get(0);
        LocalDateTime dateTime = LocalDateTime.parse(parameter.toString());

        return dateTime.toLocalTime();
    }

    @Override
    public String getName() {
        return NAME;
    }
}
