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
public class FlightResumeDetailsDto implements Serializable {
	@JsonProperty("city_name")
	private String cityName;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("price_average")
	private Double priceAverage;
	@JsonProperty("bags_price")
	private FlightBagPriceAverageDto bagsPrice;

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (final JsonProcessingException e) {
			e.printStackTrace();
			return "";
		}
	}


}

