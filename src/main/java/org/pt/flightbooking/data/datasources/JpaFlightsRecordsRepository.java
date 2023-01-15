package org.pt.flightbooking.data.datasources;

import org.pt.flightbooking.data.model.FlightRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaFlightsRecordsRepository extends CrudRepository<FlightRecord, Integer> {

}
