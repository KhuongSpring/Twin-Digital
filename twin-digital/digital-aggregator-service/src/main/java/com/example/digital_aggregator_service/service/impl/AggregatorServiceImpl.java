package com.example.digital_aggregator_service.service.impl;

import com.example.digital_aggregator_service.config.DynamicSpecConsumer;
import com.example.digital_aggregator_service.config.StaticSpecConsumer;
import com.example.digital_aggregator_service.domain.dto.response.AggregatorSpecResponseDto;
import com.example.digital_aggregator_service.domain.dto.response.DynamicSpecProducerResponseDto;
import com.example.digital_aggregator_service.domain.dto.response.StaticSpecProducerResponseDto;
import com.example.digital_aggregator_service.service.AggregatorService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class AggregatorServiceImpl implements AggregatorService {

    DynamicSpecConsumer dynamicSpecConsumer;

    StaticSpecConsumer staticSpecConsumer;

    @Override
    public AggregatorSpecResponseDto getSpec(String modelName) {
        DynamicSpecProducerResponseDto dynamicSpecs = dynamicSpecConsumer.getLatestSnapshot();

        StaticSpecProducerResponseDto staticSpecs = staticSpecConsumer.getByModel(modelName);

        System.out.println(dynamicSpecs.getSpecs().size());

        return AggregatorSpecResponseDto.builder()
                .staticSpecs(staticSpecs)
                .dynamicSpecs(dynamicSpecs)
                .build();
    }
}
