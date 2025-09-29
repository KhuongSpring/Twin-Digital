package com.example.digital_aggregator_service.domain.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AggregatorSpecResponseDto {

    List<StaticSpecGroupResponseDto> staticSpecs;

    DynamicSpecProducerResponseDto dynamicSpecs;
}
