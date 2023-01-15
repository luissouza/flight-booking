package org.pt.flightbooking.core.exception;

  public class FlightDestinyException extends RuntimeException {
    public FlightDestinyException() {
      super();
    }
    public FlightDestinyException(final String message, final Throwable cause) {
      super(message, cause);
    }
    public FlightDestinyException(final String message) {
      super(message);
    }
    public FlightDestinyException(final Throwable cause) {
      super(cause);
    }

  }