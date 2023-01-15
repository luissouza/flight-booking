package org.pt.flightbooking.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"date_from", "date_to", "average_flights"})
public class FlightHeaderResponseDto implements Serializable {

  @JsonProperty("average_flights")
  private Map<String, FlightResumeDetailsDto> averageFlights;
  @JsonProperty("date_from")
  private String dateFrom;

  @JsonProperty("date_to")
  private String dateTo;

  public String toJson() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (final JsonProcessingException e) {
      e.printStackTrace();
      return "";
    }
  }

}
