package org.pt.flightbooking.data.datasources.impl;

import org.pt.flightbooking.data.datasources.FlightsRecordsDataSource;
import org.pt.flightbooking.data.datasources.JpaFlightsRecordsRepository;
import org.pt.flightbooking.data.model.FlightRecord;
import org.springframework.stereotype.Component;

@Component
public class FlightsRecordsDataSourceImpl implements FlightsRecordsDataSource {

	final JpaFlightsRecordsRepository jpaFlightsRecordsRepository;

	public FlightsRecordsDataSourceImpl(final JpaFlightsRecordsRepository jpaFlightsRecordsRepository) {
		this.jpaFlightsRecordsRepository = jpaFlightsRecordsRepository;
	}

	@Override
	public Iterable<FlightRecord> findAll() {
		return jpaFlightsRecordsRepository.findAll();
	}

	@Override
	public void deleteAll() {
		this.jpaFlightsRecordsRepository.deleteAll();
	}

	@Override
	public void create(final FlightRecord record) {
		this.jpaFlightsRecordsRepository.save(record);
	}
}
