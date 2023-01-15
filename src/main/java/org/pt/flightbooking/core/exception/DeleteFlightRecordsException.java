package org.pt.flightbooking.core.exception;


public class DeleteFlightRecordsException extends RuntimeException {
  public DeleteFlightRecordsException() {
    super();
  }
  public DeleteFlightRecordsException(final String message, final Throwable cause) {
    super(message, cause);
  }
  public DeleteFlightRecordsException(final String message) {
    super(message);
  }
  public DeleteFlightRecordsException(final Throwable cause) {
    super(cause);
  }

}