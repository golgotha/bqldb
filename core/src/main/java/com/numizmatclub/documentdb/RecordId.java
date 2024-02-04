package com.numizmatclub.documentdb;

import java.util.Objects;

public class RecordId implements Comparable<RecordId> {

    private final int collectionId;
    private final long currentPosition;

    public RecordId(int collectionId, long currentPosition) {
        this.collectionId = collectionId;
        this.currentPosition = currentPosition;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public long getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public int compareTo(RecordId recordId) {
        int result = Integer.compare(this.collectionId, recordId.collectionId);
        if (result == 0) {
            result = Long.compare(this.currentPosition, recordId.currentPosition);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordId recordId = (RecordId) o;
        return currentPosition == recordId.currentPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPosition);
    }
}
