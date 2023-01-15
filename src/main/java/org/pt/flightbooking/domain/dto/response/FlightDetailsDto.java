package org.pt.flightbooking.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import java.io.Serializable;

@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightDetailsDto implements Serializable {

	private String id;
	private String flyFrom;
	private String cityFrom;
	private String cityCodeFrom;
	private String flyTo;
	private String cityTo;
	private String cityCodeTo;
	private Float price;
	@JsonProperty("dTime")
	private Long dTime;

	@JsonProperty("dTimeFormatted")
	private String dTimeFormatted;
	@JsonProperty("aTimeFormatted")
	private String aTimeFormatted;
	@JsonProperty("dTimeUTC")
	private Long dTimeUTC;
	@JsonProperty("aTime")
	private Long aTime;
	@JsonProperty("aTimeUTC")
	private Long aTimeUTC;
	@JsonProperty("bags_price")
	private FlightBagPriceDto baggage;

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (final JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}

}
