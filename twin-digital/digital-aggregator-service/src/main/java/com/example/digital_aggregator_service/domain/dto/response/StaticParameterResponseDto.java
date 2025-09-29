package com.example.digital_aggregator_service.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaticParameterResponseDto {

    Long id;

    String parameterName;

    String stringValue;

    Double doubleValue;

    Boolean booleanValue;

    String valueType;

    String groupName;
}
