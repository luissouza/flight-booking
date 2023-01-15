package org.pt.flightbooking.domain.entities;

import java.time.LocalDateTime;
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
public class FlightRecordEntity {

  private Integer id;
  private String flyTo;
  private String currency;
  private String dateTo;
  private String dateFrom;
  private LocalDateTime recordDateTime;

}