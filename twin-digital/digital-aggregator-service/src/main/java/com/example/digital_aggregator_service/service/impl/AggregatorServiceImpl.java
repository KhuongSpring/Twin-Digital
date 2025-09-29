package com.example.digital_aggregator_service.service.impl;

import com.example.digital_aggregator_service.config.DynamicSpecConsumer;
import com.example.digital_aggregator_service.domain.dto.response.AggregatorSpecResponseDto;
import com.example.digital_aggregator_service.domain.dto.response.DynamicSpecGroupResponseDto;
import com.example.digital_aggregator_service.domain.dto.response.DynamicSpecProducerResponseDto;
import com.example.digital_aggregator_service.service.AggregatorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AggregatorServiceImpl implements AggregatorService {

    DynamicSpecConsumer dynamicSpecConsumer;

    @Override
    public AggregatorSpecResponseDto getSpec(String modelName) {
        DynamicSpecProducerResponseDto dynamicSpecs = dynamicSpecConsumer.getLatest();

        System.out.println(dynamicSpecs.getSpecs().size());

        return AggregatorSpecResponseDto.builder()
                .dynamicSpecs(dynamicSpecs)
                .build();
    }
}
