package com.numizmatclub.documentdb.parser.function;

import java.util.List;

/**
 * @author Valerii Kantor
 */
public interface SQLFunction {

    Object execute(List<Object> params);

    /**
     * Function name used to identify a call of the fucntion.
     *
     * @return the function name, never null or empty
     */
    String getName();

}
