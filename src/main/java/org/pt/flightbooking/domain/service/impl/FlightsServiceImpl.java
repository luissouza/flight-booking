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
import org.pt.flightbooking.data.webclient.rest.WebClientRequest;
import org.pt.flightbooking.data.dto.request.FlightSearchParams;
import org.pt.flightbooking.domain.dto.response.FlightBagPriceAverageDto;
import org.pt.flightbooking.domain.dto.response.FlightDetailsDto;
import org.pt.flightbooking.domain.dto.response.FlightHeaderResponseDto;
import org.pt.flightbooking.domain.dto.response.FlightResponseDto;
import org.pt.flightbooking.domain.dto.response.FlightResumeDetailsDto;
import org.pt.flightbooking.domain.dto.response.LocationDto;
import org.pt.flightbooking.core.exception.FlightDestinyException;
import org.pt.flightbooking.domain.entities.FlightRecordEntity;
import org.pt.flightbooking.domain.repository.FlightsRecordsRepository;
import org.pt.flightbooking.domain.service.FlightsService;
import org.pt.flightbooking.utils.DateTimeFormatterConfig;
import org.pt.flightbooking.utils.NumberUtilsConfig;
import org.pt.flightbooking.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.pt.flightbooking.core.exception.AverageFlightsException;

@Slf4j
@Component
public class FlightsServiceImpl implements FlightsService {

  private final FlightsRecordsRepository flightsRecordsRepository;
  private final WebClientRequest webClientFulfillmentRequest;

  public FlightsServiceImpl(final FlightsRecordsRepository flightsRecordsRepository, final WebClientRequest webClientFulfillmentRequest) {
    this.flightsRecordsRepository = flightsRecordsRepository;
    this.webClientFulfillmentRequest = webClientFulfillmentRequest;
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
          .dateFrom(DateTimeFormatterConfig.convertIsoFormat(params.getDateFrom()))
          .averageFlights(resume).build();

      log.info("Flights AVG response {} ", flightHeaderResponseDto.toJson());

      return Optional.of(flightHeaderResponseDto);

    } catch (final Exception e) {
      throw new AverageFlightsException(e);
    }
  }

  public FlightResponseDto getSkyPickerFlights(final FlightSearchParams params) {
    final FlightResponseDto response = (FlightResponseDto) webClientFulfillmentRequest.getSkyPickerFlights(params).getBody();
    log.info("Flights from skyPicker {} ", Objects.requireNonNull(response).toJson());
    return response;
  }

  public Map<String, List<FlightDetailsDto>> groupFlightsByDestiny(final FlightResponseDto Dto) {
    final Map<String, List<FlightDetailsDto>> flightsAggPerDestination = Dto.getData().stream().collect(groupingBy(FlightDetailsDto::getFlyTo));
    return flightsAggPerDestination;
  }

  public void saveRecord(final FlightSearchParams params) {

    final FlightRecordEntity record = new FlightRecordEntity();
    record.setFlyTo(params.getFlyTo());
    record.setCurrency(params.getCurrency());
    record.setDateTo(params.getDateTo());
    record.setDateFrom(params.getDateFrom());
    record.setRecordDateTime(LocalDateTime.now());

    flightsRecordsRepository.create(record);
  }

  public void calcAvg(final Map<String, FlightResumeDetailsDto> res, final String destination,
                      final List<FlightDetailsDto> flights, final FlightResponseDto flightsDto
  ) throws AverageFlightsException {

    try {

      final FlightDetailsDto flyDetails = flights.stream().findFirst().get();

      final Double priceAvg = flights.stream().collect(Collectors.averagingDouble(p -> p.getPrice()));

      final Double baggageOneAveragePriceFlyTo = flights.stream().collect(Collectors.averagingDouble(p -> p.getBaggage().getBagOnePrice()));

      final Double baggageTwoAveragePriceFlyTo = flights.stream().collect(Collectors.averagingDouble(p -> p.getBaggage().getBagTwoPrice()));

      final FlightBagPriceAverageDto bagsAverage = FlightBagPriceAverageDto.builder()
          .bagOneAveragePrice(NumberUtilsConfig.round(baggageOneAveragePriceFlyTo))
          .bagTwoAveragePrice(NumberUtilsConfig.round(baggageTwoAveragePriceFlyTo)).build();

      final FlightResumeDetailsDto detailsFlyTo = FlightResumeDetailsDto.builder()
          .priceAverage(NumberUtilsConfig.round(priceAvg))
          .cityName(flyDetails.getCityTo())
          .currency(flightsDto.getCurrency())
          .bagsPrice(bagsAverage).build();

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
            "The flight codes is invalid. Please insert TWO AIRPORT CODES separated by commas, example: (OPO,LIS) or (LIS,OPO) to fetch data from PORTO and LISBON flights. Consult the link: https://airportcodes.aero/iata/ and see if the codes are valid."
        );
      }

      final String locationCodeOne = StringUtils.replaceSpecialChars(airPorts[0].toString());
      final String locationCodeTwo = StringUtils.replaceSpecialChars(airPorts[1].toString());
      final String locations = locationCodeOne.concat(",").concat(locationCodeTwo);
      final LocationDto locationOne = (LocationDto) webClientFulfillmentRequest.getLocation(locationCodeOne).getBody();
      final LocationDto locationTwo = (LocationDto) webClientFulfillmentRequest.getLocation(locationCodeTwo).getBody();

      if(locationOne.getLocations().size() == 0 || locationTwo.getLocations().size() == 0) {
        throw new FlightDestinyException(
            "At least one of the airport codes are invalid. Consult the link: https://airportcodes.aero/iata/ and see if the codes are valid."
        );
      }

      return locations;

    } catch (final Exception e) {
      throw new FlightDestinyException(
          "The flight codes is invalid. Please insert two airport codes separated by commas, example: (OPO,LIS) or (LIS,OPO) to fetch data from PORTO and LISBON flights. Consult the link: https://airportcodes.aero/iata/ and see if the codes are valid.", e
      );
    }
  }

}
