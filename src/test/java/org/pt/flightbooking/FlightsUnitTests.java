package org.pt.flightbooking;

import org.pt.flightbooking.data.dto.request.FlightSearchParams;
import org.pt.flightbooking.domain.dto.response.FlightHeaderResponseDto;
import org.pt.flightbooking.domain.service.FlightsRecordsService;
import org.pt.flightbooking.domain.service.FlightsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
public class FlightsUnitTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FlightsService flightsService;

	@Autowired
	private FlightsRecordsService flightsRecordsService;


	@Test
	public void test_get_all_flights() {

		final String flyTo = "LIS,OPO";
		final String currency = "GBP";
		final String dateFrom = "2023-03-01";
		final String dateTo = "2023-03-10";

		final FlightSearchParams params = FlightSearchParams.builder().flyTo(flyTo).currency(currency).dateFrom(dateFrom).dateTo(dateTo).build();
		this.flightsService.filterFlights(params);
		final var response = (Optional<FlightHeaderResponseDto>) this.flightsService.filterFlights(params);
		assertThat(response.isPresent()).isTrue();

	}

	@Test
	public void test_get_all_flight_records() throws Exception {
		mockMvc.perform(get("/api/v1/flight/records"))
				.andExpectAll()
				.andExpect(status().isOk());
	}

	@Test
	public void test_get_all_flight_records_service() {
		final Optional<?> flightRecords =  this.flightsRecordsService.filterFlightsRecords();
		assertThat(flightRecords.isPresent()).isTrue();
	}

	@Test
	public void test_delete_flight_records() throws Exception {
		mockMvc.perform(delete("/api/v1/flight/records")).andExpect(status().isAccepted());
	}

	@Test
	public void test_delete_flight_records_service() throws Exception {

		try {
			this.flightsRecordsService.deleteAll();
			assertThat(Boolean.TRUE).isTrue();
		} catch(final Exception e) {
			assertThat(Boolean.FALSE).isTrue();
		}

	}
}
