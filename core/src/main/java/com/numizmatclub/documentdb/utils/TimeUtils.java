package com.numizmatclub.documentdb.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Valerii Kantor
 */
public final class TimeUtils {

    private TimeUtils() {}

    public static LocalDateTime convertEpochToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
