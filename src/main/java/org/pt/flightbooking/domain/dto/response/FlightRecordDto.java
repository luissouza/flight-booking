package org.pt.flightbooking.domain.dto.response;

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
public class FlightRecordDto implements Serializable {

  private String currency;
  private String dateFrom;
  private String dateTo;
  private String flyTo;
  private String recordDateTime;

  public String toJson() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (final JsonProcessingException e) {
      e.printStackTrace();
      return "";
    }
  }
}
