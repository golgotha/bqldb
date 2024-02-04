package com.numizmatclub.documentdb.parser.expression;

/**
 * @author Valerii Kantor
 */
public class NotEqualsCompareOperator implements CompareOperator {

    @Override
    public boolean execute(Object left, Object right) {
        return compareNotEquals(left, right);
    }

    private boolean compareNotEquals(Object left, Object right) {
        if ((left ==null && right != null) ||
                (left != null && right == null)) {
            return true;
        }
        return !left.equals(right);
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
