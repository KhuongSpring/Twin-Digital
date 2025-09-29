package com.example.digital_aggregator_service.config;

import com.example.digital_aggregator_service.domain.dto.response.DynamicSpecProducerResponseDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DynamicSpecConsumer {
    private final Map<String, DynamicSpecProducerResponseDto> cache = new ConcurrentHashMap<>();

    @KafkaListener(topics = "dynamic-spec-topic", groupId = "aggregator-group")
    public void listen(DynamicSpecProducerResponseDto specs) {
        System.out.println("Aggregator received dynamic specs: " + specs.getSpecs());
        cache.put("latest", specs);
    }

    public DynamicSpecProducerResponseDto getLatest() {
        return cache.getOrDefault("latest", new DynamicSpecProducerResponseDto(Collections.emptyList()));
    }

}
