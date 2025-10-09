package com.example.continuous_data_service.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicSpecGroupResponseDto {

    String groupName;

    List<DynamicParameterResponseDto> parameters;
}
