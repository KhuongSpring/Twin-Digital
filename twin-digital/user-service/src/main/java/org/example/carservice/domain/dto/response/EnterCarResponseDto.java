package org.example.carservice.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnterCarResponseDto {

  String carModelName;

  String userId;

  List<StaticSpecGroupResponseDto> staticSpecGroups;

  List<DynamicSpecGroupResponseDto> dynamicSpecGroups;

  String message;
}
