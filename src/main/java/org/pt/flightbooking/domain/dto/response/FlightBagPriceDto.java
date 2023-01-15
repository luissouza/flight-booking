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
public class FlightBagPriceDto implements Serializable {

	@JsonProperty("1")
	private Double bagOnePrice = 0.00;
	@JsonProperty("2")
	private Double bagTwoPrice = 0.00;

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (final JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}
}
