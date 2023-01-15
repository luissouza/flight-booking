package org.pt.flightbooking.domain.service;

import org.pt.flightbooking.data.dto.request.FlightSearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;
public interface FlightsService {

    Logger log = LoggerFactory.getLogger(FlightsService.class);

    @Cacheable(value = "cacheStore", keyGenerator = "customKeyGenerator")
    Optional<?> filterFlights(FlightSearchParams params);
}
