package org.pt.flightbooking.domain.repository;

import java.util.Optional;
import org.pt.flightbooking.domain.entities.FlightRecordEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

public interface FlightsRecordsRepository {

  public Optional<Iterable<FlightRecordEntity>> findAll();
  public void deleteAll();
  public void create(FlightRecordEntity record);

}
