package org.pt.flightbooking.domain.service.impl;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.pt.flightbooking.domain.service.FlightsRecordsService;
import org.pt.flightbooking.core.exception.DeleteFlightRecordsException;
import org.pt.flightbooking.core.exception.FlightsDataNotFoundException;
import org.pt.flightbooking.domain.repository.FlightsRecordsRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FlightsRecordsServiceImpl implements FlightsRecordsService {

  private final FlightsRecordsRepository flightsRecordsRepository;

  public FlightsRecordsServiceImpl(final FlightsRecordsRepository repository) {
    this.flightsRecordsRepository = repository;
  }

  @Override
  public Optional<?> filterFlightsRecords() {
    return Optional.of(this.flightsRecordsRepository.findAll()).orElseThrow(FlightsDataNotFoundException::new);
  }

  @Override
  public void deleteAll() {
    try {
      this.flightsRecordsRepository.deleteAll();
    } catch (final Exception e) {
      throw new DeleteFlightRecordsException(e);
    }
  }

}
