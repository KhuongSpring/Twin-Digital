package com.example.digital_aggregator_service.service.impl;

import com.example.digital_aggregator_service.config.DynamicSpecConsumer;
import com.example.digital_aggregator_service.config.StaticSpecConsumer;
import com.example.digital_aggregator_service.domain.dto.response.AggregatorSpecResponseDto;
import com.example.digital_aggregator_service.domain.dto.response.DynamicSpecGroupResponseDto;
import com.example.digital_aggregator_service.domain.dto.response.DynamicSpecProducerResponseDto;
import com.example.digital_aggregator_service.domain.dto.response.StaticSpecProducerResponseDto;
import com.example.digital_aggregator_service.service.AggregatorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.List;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AggregatorServiceImpl implements AggregatorService {

    DynamicSpecConsumer dynamicSpecConsumer;

    StaticSpecConsumer staticSpecConsumer;

    // Constructor với log
    public AggregatorServiceImpl(DynamicSpecConsumer dynamicSpecConsumer, StaticSpecConsumer staticSpecConsumer) {
        this.dynamicSpecConsumer = dynamicSpecConsumer;
        this.staticSpecConsumer = staticSpecConsumer;
        System.out.println("=== AGGREGATOR SERVICE INITIALIZED ===");
        System.out.println("DynamicSpecConsumer: " + dynamicSpecConsumer);
        System.out.println("StaticSpecConsumer: " + staticSpecConsumer);
        System.out.println("=== END INITIALIZATION ===");
    }

    @PostConstruct
    public void init() {
        System.out.println("=== AGGREGATOR SERVICE POST CONSTRUCT ===");
        System.out.println("Service is ready to receive API calls");
        System.out.println("Waiting for Kafka messages on topics:");
        System.out.println("- dynamic-spec-topic");
        System.out.println("- static-spec-topic");
        System.out.println("=== END POST CONSTRUCT ===");
    }

    @Override
    public AggregatorSpecResponseDto getSpec(String modelName) {
        DynamicSpecProducerResponseDto dynamicSpecs = dynamicSpecConsumer.getLatest();

        StaticSpecProducerResponseDto staticSpecs = staticSpecConsumer.getByModel(modelName);

        System.out.println(dynamicSpecs.getSpecs().size());

        return AggregatorSpecResponseDto.builder()
                .staticSpecs(staticSpecs)
                .dynamicSpecs(dynamicSpecs)
                .build();
    }
}
