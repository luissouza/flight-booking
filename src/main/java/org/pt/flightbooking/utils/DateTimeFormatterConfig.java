package org.pt.flightbooking.utils;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterConfig {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    public static String formatLocalDateTime(final Long epochSeconds) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        final LocalDateTime ldt = Instant.ofEpochSecond(epochSeconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
        return ldt.format(formatter);
    }

    public static String convertIsoFormat(final String dateIso) throws ParseException {
        final LocalDate date = LocalDate.parse(dateIso);
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return date.format(formatter);
    }
}
