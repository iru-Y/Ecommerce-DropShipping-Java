package com.delivery.trizi.trizi.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class GetHour {
    public static String getHour () {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z")
                .withZone(zoneId);
        return formatter.format(instant);
    }
}
