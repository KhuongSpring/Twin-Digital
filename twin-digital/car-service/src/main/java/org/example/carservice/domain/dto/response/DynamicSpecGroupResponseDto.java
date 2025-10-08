package org.example.carservice.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicSpecGroupResponseDto {

  String groupName;

  List<DynamicParameterResponseDto> parameters;
}
