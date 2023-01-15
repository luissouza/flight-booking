package org.pt.flightbooking.data.dto.request;

import com.google.gson.Gson;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class FlightSearchParams implements Serializable {
	private String currency;
	private String dateFrom;
	private String dateTo;
	private String flyTo;
	private String flyFrom;

	private String airLines;

	public FlightSearchParams(
			final String currency, final String dateFrom, final String dateTo, final String flyTo, final String airLines) {
		this.currency = currency;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.flyTo = flyTo;
		this.airLines = airLines;
	}

	public String toJson() {
		try {
			return new Gson().toJson(this);
		} catch (final Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
