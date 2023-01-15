package org.pt.flightbooking.domain.service;

import java.util.Optional;

public interface FlightsRecordsService {

    Optional<?> filterFlightsRecords();

    void deleteAll();
}
