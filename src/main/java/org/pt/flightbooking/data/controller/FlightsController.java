package org.pt.flightbooking.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.pt.flightbooking.domain.service.FlightsService;
import org.pt.flightbooking.data.dto.request.FlightSearchParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("api/v1")
public class FlightsController {

	private final FlightsService flightsService;

    public FlightsController(final FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @Tag(name = "FlightsController")
    @Operation(summary = "Get flights average")
    @RequestMapping(value = "flights/avg", method = RequestMethod.GET, headers="Accept=application/json")
    public ResponseEntity<?> filterFlights(@PathParam("flyTo") final String flyTo,
                                           @PathParam("currency") final String currency,
                                           @PathParam("dateFrom") final String dateFrom,
                                           @PathParam("dateTo") final String dateTo,
                                           @PathParam("airLines") final String airLines) {

        return new ResponseEntity<>(flightsService.filterFlights(new FlightSearchParams(currency, dateFrom, dateTo, flyTo, airLines)).get(), HttpStatus.OK);
    }

}
