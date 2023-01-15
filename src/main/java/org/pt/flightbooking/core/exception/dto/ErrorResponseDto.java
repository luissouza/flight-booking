package org.pt.flightbooking.core.exception.dto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseDto {

  private String timestamp;
  private Integer httpCode;
  private String message;
  private String detail;
}
