package org.pt.flightbooking.configuration;

import org.pt.flightbooking.FlightBookingApplication;
import org.pt.flightbooking.domain.service.FlightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest(classes = FlightBookingApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"dev"})
@ContextConfiguration
@CucumberContextConfiguration
public abstract class ConfigurationAutomatedTest {

    @Autowired
    public FlightsService flightsService;

}
