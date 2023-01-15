package org.pt.flightbooking.utils;

import java.util.Map;

public class WebClientUtilsConfig {

  public static String generateUrlParams(final Map<String, Object> mapParams) {
    final StringBuffer stringBuffer = new StringBuffer("?");
    mapParams.entrySet()
        .forEach(e -> stringBuffer.append(e.getKey()).append("=").append(e.getValue()).append("&"));
    return stringBuffer.toString();
  }
}
