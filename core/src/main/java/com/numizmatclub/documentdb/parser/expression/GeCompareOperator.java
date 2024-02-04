package com.numizmatclub.documentdb.parser.expression;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * The implementation of "greater or equal than" comparison operation.
 *
 * @author Valerii Kantor
 */
public class GeCompareOperator implements CompareOperator {

    @Override
    public boolean execute(Object left, Object right) {
        if (left instanceof LocalTime leftTime) {
            if (right instanceof String) {
                LocalTime rightValue = LocalTime.parse((CharSequence) right);
                return leftTime.compareTo(rightValue) >= 0;
            } else if (right instanceof LocalTime rightValue) {
                return leftTime.compareTo(rightValue) >= 0;
            } else if (right instanceof LocalDateTime rightValue) {
                return leftTime.compareTo(rightValue.toLocalTime()) >= 0;
            }
        }

        if (!(left instanceof Comparable)) {
            return false;
        }
        return ((Comparable<Object>) left).compareTo(right) >= 0;
    }
}
