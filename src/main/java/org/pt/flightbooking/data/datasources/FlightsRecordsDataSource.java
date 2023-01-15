package org.pt.flightbooking.data.datasources;

import org.pt.flightbooking.data.model.FlightRecord;

public interface FlightsRecordsDataSource {

	public Iterable<FlightRecord> findAll();
	public void deleteAll();
	public void create(FlightRecord record);

}

