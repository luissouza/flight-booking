package org.pt.flightbooking.data.repository;

import java.util.Optional;
import org.pt.flightbooking.data.datasources.FlightsRecordsDataSource;
import org.pt.flightbooking.data.dto.mapper.FlightRecordEntityMapper;
import org.pt.flightbooking.domain.entities.FlightRecordEntity;
import org.pt.flightbooking.domain.repository.FlightsRecordsRepository;
import org.springframework.stereotype.Component;

@Component
public class FlightsRecordsRepositoryImpl implements FlightsRecordsRepository {

  private final FlightsRecordsDataSource flightsRecordsDataSource;

  public FlightsRecordsRepositoryImpl(final FlightsRecordsDataSource flightsRecordsDataSource) {
    this.flightsRecordsDataSource = flightsRecordsDataSource;
  }

  @Override
  public Optional<Iterable<FlightRecordEntity>> findAll() {
    return Optional.of(FlightRecordEntityMapper.convertModelToEntity(flightsRecordsDataSource.findAll()));
  }

  @Override
  public void deleteAll() {
    flightsRecordsDataSource.deleteAll();
  }

  @Override
  public void create(final FlightRecordEntity record) {
    flightsRecordsDataSource.create(FlightRecordEntityMapper.convertEntityToModel(record).get());

  }
}
