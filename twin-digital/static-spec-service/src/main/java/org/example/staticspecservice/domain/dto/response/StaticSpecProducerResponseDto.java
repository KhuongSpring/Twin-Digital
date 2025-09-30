package org.example.staticspecservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StaticSpecProducerResponseDto {
  private List<StaticSpecGroupResponseDto> specs;
}