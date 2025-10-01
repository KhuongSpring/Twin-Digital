package com.example.digital_aggregator_service.config;

import com.example.digital_aggregator_service.domain.dto.response.StaticSpecProducerResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class StaticSpecConsumer {
    private final ConcurrentHashMap<String, StaticSpecProducerResponseDto> cache = new ConcurrentHashMap<>();

    @KafkaListener(topics = "static-spec-topic", groupId = "aggregator-group")
    public void listen(StaticSpecProducerResponseDto specs) {
        cache.put("latest", specs);
    }

    public StaticSpecProducerResponseDto getByModel(String modelName) {
        StaticSpecProducerResponseDto result = cache.getOrDefault("latest",
                new StaticSpecProducerResponseDto(Collections.emptyList()));
        return result;
    }
}