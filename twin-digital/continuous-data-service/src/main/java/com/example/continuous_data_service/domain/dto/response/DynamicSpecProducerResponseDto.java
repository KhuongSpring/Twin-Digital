package com.example.continuous_data_service.domain.dto.response;

import com.example.continuous_data_service.domain.dto.response.DynamicSpecGroupResponseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicSpecProducerResponseDto {
    List<DynamicSpecGroupResponseDto> specs;
}
