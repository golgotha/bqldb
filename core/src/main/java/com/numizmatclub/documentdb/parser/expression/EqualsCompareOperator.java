package com.numizmatclub.documentdb.parser.expression;

/**
 * @author Valerii Kantor
 */
public class EqualsCompareOperator implements CompareOperator {

    @Override
    public boolean execute(Object left, Object right) {
        return compareEquals(left, right);
    }

    private boolean compareEquals(Object left, Object right) {
        if (left == null || right == null) {
            return false;
        }

        if (left == right) {
            return true;
        }

        return left.equals(right);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
