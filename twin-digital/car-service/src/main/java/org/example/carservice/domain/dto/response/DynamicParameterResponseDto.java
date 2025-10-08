package org.example.carservice.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicParameterResponseDto {

  String paramName;

  String stringValue;

  Double numericValue;

  String valueType;

  String groupName;
}
