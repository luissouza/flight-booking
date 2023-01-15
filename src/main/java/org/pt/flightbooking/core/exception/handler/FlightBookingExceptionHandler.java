package org.pt.flightbooking.core.exception.handler;

import static org.pt.flightbooking.core.exception.message.ErrorMessage.exchangeRequestError;

import javax.servlet.http.HttpServletRequest;
import org.pt.flightbooking.core.exception.AverageFlightsException;
import org.pt.flightbooking.core.exception.DeleteFlightRecordsException;
import org.pt.flightbooking.core.exception.FlightDestinyException;
import org.pt.flightbooking.core.exception.FlightsDataNotFoundException;
import org.pt.flightbooking.core.exception.dto.ErrorResponseDto;
import org.pt.flightbooking.core.exception.error.ErrorBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class FlightBookingExceptionHandler extends ResponseEntityExceptionHandler {

	private final ErrorBuilder errorBuilder;

	public FlightBookingExceptionHandler(
			final ErrorBuilder errorBuilder
	) {
		this.errorBuilder = errorBuilder;
	}

	@ExceptionHandler(AverageFlightsException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponseDto handleException(final AverageFlightsException exception, final HttpServletRequest request) {
		return errorBuilder.createError(exchangeRequestError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FlightDestinyException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponseDto handleException(final FlightDestinyException exception, final HttpServletRequest request) {
		return errorBuilder.createError(exchangeRequestError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DeleteFlightRecordsException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponseDto handleException(final DeleteFlightRecordsException exception, final HttpServletRequest request) {
		return errorBuilder.createError(exchangeRequestError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(FlightsDataNotFoundException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ErrorResponseDto handleException(final FlightsDataNotFoundException exception, final HttpServletRequest request) {
		return errorBuilder.createError(exchangeRequestError(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
