package org.pt.flightbooking.data.webclient.rest;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.pt.flightbooking.data.webclient.constant.SkyPicker;
import org.pt.flightbooking.core.configuration.webclient.WebClientHandler;
import org.pt.flightbooking.core.configuration.webclient.WebClientProperties;
import org.pt.flightbooking.core.configuration.webclient.WebClientPropertiesService;
import org.pt.flightbooking.data.dto.request.FlightSearchParams;
import org.pt.flightbooking.domain.dto.response.FlightResponseDto;
import org.pt.flightbooking.domain.dto.response.LocationDto;
import org.pt.flightbooking.core.exception.error.ErrorBuilder;
import org.pt.flightbooking.core.exception.mappings.ExceptionMapper;
import org.pt.flightbooking.utils.WebClientUtilsConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class WebClientRequest extends WebClientHandler {

  private final WebClientPropertiesService service;
  private final WebClientProperties properties;

  public WebClientRequest(final WebClient webClient,
      final WebClientProperties properties,
      final ErrorBuilder errorBuilder,
      final ExceptionMapper exceptionMapper) {

    super(webClient, properties, errorBuilder, exceptionMapper);
    this.properties = properties;
    this.service = properties.getService(SkyPicker.Endpoint.label);
  }

  public ResponseEntity<?> getSkyPickerFlights(final FlightSearchParams params) {

    final Integer v = properties.getV(service, SkyPicker.Flights.label);

    final Map<String, Object> mapParams = new HashMap<>();
    mapParams.put("fly_from", params.getFlyFrom());
    mapParams.put("fly_to", params.getFlyTo());
    mapParams.put("v", v);
    mapParams.put("curr", params.getCurrency());
    mapParams.put("depart_after", params.getDateFrom());
    mapParams.put("depart_before", params.getDateTo());
    mapParams.put("select_airlines", params.getAirLines());

    return retrieveQueryParams(WebClientUtilsConfig.generateUrlParams(mapParams), SkyPicker.Endpoint.label, service, SkyPicker.Flights.label,
        FlightResponseDto.class);

  }

  public ResponseEntity<?> getLocation(final String iata) {
    final Map<String, Object> mapParams = new HashMap<>();
    mapParams.put("id", iata);

    return retrieveQueryParams(WebClientUtilsConfig.generateUrlParams(mapParams), SkyPicker.Endpoint.label, service, SkyPicker.Locations.label,
        LocationDto.class);

  }
}