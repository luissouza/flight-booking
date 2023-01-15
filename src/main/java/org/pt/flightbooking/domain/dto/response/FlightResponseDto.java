package org.pt.flightbooking.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import lombok.*;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class FlightResponseDto implements Serializable {
	@JsonProperty("search_id")
	private String searchId;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("fx_rate")
	private Float fxRate;
	@JsonProperty("data")
	private List<FlightDetailsDto> data;

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (final JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
