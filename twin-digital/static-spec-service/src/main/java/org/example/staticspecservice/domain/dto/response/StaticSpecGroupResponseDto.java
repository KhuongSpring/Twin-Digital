package org.example.staticspecservice.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaticSpecGroupResponseDto {

    Long id;

    String groupName;

    List<StaticParameterResponseDto> parameters;
}
