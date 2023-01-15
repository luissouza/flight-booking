package org.pt.flightbooking.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class StringUtils {

    public static Map<String, String> convertCucumberDataTableToMap(
        final List<List<String>> dataList) {
        try {
            final Map<String, String> mapDataList = new HashMap<>();
            dataList.forEach(row -> {
                mapDataList.put(row.get(0), row.get(1));
            });
            return mapDataList;
        } catch (final Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static String replaceSpecialChars(final String value) {
        if (value == null)
            return "";
        final String pattern = "[^a-zA-Z0-9]";
        return value.replaceAll(pattern, "");
    }

}
