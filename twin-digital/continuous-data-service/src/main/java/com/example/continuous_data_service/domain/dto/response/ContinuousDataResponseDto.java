package com.example.continuous_data_service.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ContinuousDataResponseDto {
    DynamicSpecProducerResponseDto dynamicSpecs;
}
