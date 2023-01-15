package org.pt.flightbooking.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlightBagPriceAverageDto implements Serializable {

  @JsonProperty("bag_one_average_price")
  private Double bagOneAveragePrice = 0.00;
  @JsonProperty("bag_two_average_price")
  private Double bagTwoAveragePrice = 0.00;

  public String toJson() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (final JsonProcessingException e) {
      e.printStackTrace();
      return "";
    }
  }
}
