package com.example.dynamic_spec_service.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicParameterResponseDto {

    String paramName;

    String stringValue;

    Double numericValue;

    String valueType;

    String groupName;
}
