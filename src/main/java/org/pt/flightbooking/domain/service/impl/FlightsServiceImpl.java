package org.pt.flightbooking.domain.service.impl;

import static java.util.stream.Collectors.groupingBy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.pt.flightbooking.core.exception.AverageFlightsException;
import org.pt.flightbooking.core.exception.FlightDestinyException;
import org.pt.flightbooking.data.dto.request.FlightSearchParams;
import org.pt.flightbooking.data.webclient.rest.WebClientRequest;
import org.pt.flightbooking.domain.dto.response.FlightBagPriceAverageDto;
import org.pt.flightbooking.domain.dto.response.FlightDetailsDto;
import org.pt.flightbooking.domain.dto.response.FlightHeaderResponseDto;
import org.pt.flightbooking.domain.dto.response.FlightResponseDto;
import org.pt.flightbooking.domain.dto.response.FlightResumeDetailsDto;
import org.pt.flightbooking.domain.dto.response.LocationDto;
import org.pt.flightbooking.domain.entities.FlightRecordEntity;
import org.pt.flightbooking.domain.repository.FlightsRecordsRepository;
import org.pt.flightbooking.domain.service.FlightsService;
import org.pt.flightbooking.utils.DateTimeFormatterConfig;
import org.pt.flightbooking.utils.NumberUtilsConfig;
import org.pt.flightbooking.utils.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FlightsServiceImpl implements FlightsService {

  private final FlightsRecordsRepository flightsRecordsRepo;
  private final WebClientRequest webClient;

  public FlightsServiceImpl(final FlightsRecordsRepository flightsRecordsRepo, final WebClientRequest webClient) {
    this.flightsRecordsRepo = flightsRecordsRepo;
    this.webClient = webClient;
  }

  @Override
  public Optional<?> filterFlights(final FlightSearchParams params) {

    log.info("filterFlights - Method Filter Flights Started: {} ", params.toJson());
    params.setFlyFrom(validateAirports(params));

    try {

      final FlightResponseDto flightsDto = getSkyPickerFlights(params);
      final Map<String, List<FlightDetailsDto>> flightsAggPerDestination = groupFlightsByDestiny(flightsDto);
      final Map<String, FlightResumeDetailsDto> resume = new HashMap<>();
      flightsAggPerDestination.forEach((key, value) -> calcAvg(resume, key, value, flightsDto));
      this.saveRecord(params);

      final FlightHeaderResponseDto flightHeaderResponseDto = FlightHeaderResponseDto.builder()
          .dateTo(DateTimeFormatterConfig.convertIsoFormat(params.getDateTo()))
          .dateFrom(DateTimeFormatterConfig.convertIsoFormat(params.getDateFrom())).averageFlights(resume).build();

      log.info("Flights AVG response {} ", flightHeaderResponseDto.toJson());

      return Optional.of(flightHeaderResponseDto);

    } catch (final Exception e) {
      throw new AverageFlightsException(e);
    }
  }


  public FlightResponseDto getSkyPickerFlights(final FlightSearchParams params) {
    final FlightResponseDto response = (FlightResponseDto) webClient.getSkyPickerFlights(params).getBody();
    log.info("Flights from skyPicker {} ", Objects.requireNonNull(response).toJson());
    return response;
  }

  public Map<String, List<FlightDetailsDto>> groupFlightsByDestiny(final FlightResponseDto Dto) {
    return Dto.getData().stream().collect(groupingBy(FlightDetailsDto::getFlyTo));
  }

  public void saveRecord(final FlightSearchParams params) {

    final FlightRecordEntity record = FlightRecordEntity.builder().flyTo(params.getFlyTo())
        .currency(params.getCurrency()).dateTo(params.getDateTo()).dateFrom(params.getDateFrom())
        .recordDateTime(LocalDateTime.now()).build();

    flightsRecordsRepo.create(record);
  }

  public void calcAvg(final Map<String, FlightResumeDetailsDto> res, final String destination,
      final List<FlightDetailsDto> flights, final FlightResponseDto flightsDto) throws AverageFlightsException {

    try {

      final FlightDetailsDto flyDetails = flights.stream().findFirst().get();

      final Double priceAvg = flights.stream().collect(Collectors.averagingDouble(FlightDetailsDto::getPrice));

      final Double bagOneAvg = flights.stream()
          .collect(Collectors.averagingDouble(p -> p.getBaggage().getBagOnePrice()));

      final Double bagTwoAvg = flights.stream()
          .collect(Collectors.averagingDouble(p -> p.getBaggage().getBagTwoPrice()));

      final FlightBagPriceAverageDto bagsAverage = FlightBagPriceAverageDto.builder()
          .bagOneAveragePrice(NumberUtilsConfig.round(bagOneAvg)).bagTwoAveragePrice(NumberUtilsConfig.round(bagTwoAvg))
          .build();

      final FlightResumeDetailsDto detailsFlyTo = FlightResumeDetailsDto.builder()
          .priceAverage(NumberUtilsConfig.round(priceAvg)).cityName(flyDetails.getCityTo())
          .currency(flightsDto.getCurrency()).bagsPrice(bagsAverage).build();

      res.put(destination, detailsFlyTo);

    } catch (final Exception e) {
      throw new AverageFlightsException(e);
    }

  }

  public String validateAirports(final FlightSearchParams params) {

    try {

      final String[] airPorts = params.getFlyTo().trim().split(",");

      if (airPorts.length != 2) {
        throw new FlightDestinyException(
            "The flight codes is invalid. Please insert TWO AIRPORT CODES separated by commas, "
                + "example: (OPO,LIS) or (LIS,OPO) to fetch data from PORTO and LISBON flights. "
                + "Consult link: https://airportcodes.aero/iata/ and see if the codes are valid.");
      }

      final String locationCodeOne = StringUtils.replaceSpecialChars(airPorts[0]);
      final String locationCodeTwo = StringUtils.replaceSpecialChars(airPorts[1]);
      final String locations = locationCodeOne.concat(",").concat(locationCodeTwo);
      final LocationDto locationOne = (LocationDto) webClient.getLocation(locationCodeOne).getBody();
      final LocationDto locationTwo = (LocationDto) webClient.getLocation(locationCodeTwo).getBody();

      if (Objects.requireNonNull(locationOne).getLocations().size() == 0
          || Objects.requireNonNull(locationTwo).getLocations().size() == 0) {
        throw new FlightDestinyException("At least one of the airport codes are invalid. "
            + "Consult link: https://airportcodes.aero/iata/ and see if the codes are valid.");
      }

      return locations;

    } catch (final Exception e) {
      throw new FlightDestinyException(
          "The flight codes is invalid. Please insert two airport codes separated by commas,"
              + " example: (OPO,LIS) or (LIS,OPO) to fetch data from PORTO and LISBON flights. "
              + "Consult link: https://airportcodes.aero/iata/ and see if the codes are valid.", e);
    }
  }

}
