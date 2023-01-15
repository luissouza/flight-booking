package org.pt.flightbooking.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pt.flightbooking.domain.service.FlightsRecordsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class FlightsRecordsController {

	private final FlightsRecordsService flightsRecordsService;

    public FlightsRecordsController(final FlightsRecordsService flightsRecordsService) {
        this.flightsRecordsService = flightsRecordsService;
    }
    @Tag(name = "FlightsRecordsController")
    @Operation(summary = "Get all flight records")
    @RequestMapping(value = "flight/records", method = RequestMethod.GET, headers="Accept=application/json")
    public ResponseEntity<?> filterFlightsRecords() {
        return new ResponseEntity<>(flightsRecordsService.filterFlightsRecords().get(), HttpStatus.OK);
    }
    @Tag(name = "FlightsRecordsController")
    @Operation(summary = "Delete all flight records")
    @RequestMapping(value = "flight/records", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        flightsRecordsService.deleteAll();
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
