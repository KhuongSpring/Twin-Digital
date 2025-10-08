package com.example.digital_aggregator_service.config;

import com.example.digital_aggregator_service.domain.dto.response.DynamicSpecProducerResponseDto;
import com.example.digital_aggregator_service.domain.dto.response.StaticSpecProducerResponseDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@Getter
public class StaticSpecConsumer {

    private volatile StaticSpecProducerResponseDto latestSnapshot =
            new StaticSpecProducerResponseDto(Collections.emptyList());

    private volatile StaticSpecProducerResponseDto latestUpdate =
            new StaticSpecProducerResponseDto(Collections.emptyList());

    @KafkaListener(
            topics = "static-spec-snapshot-topic",
            groupId = "aggregator-group",
            containerFactory = "staticSpecKafkaListenerContainerFactory"
    )
    public void listenSnapshot(StaticSpecProducerResponseDto specs) {
        this.latestSnapshot = specs;
    }

    @KafkaListener(
            topics = "static-spec-update-topic",
            groupId = "aggregator-group",
            containerFactory = "staticSpecKafkaListenerContainerFactory"
    )
    public void listenUpdate(StaticSpecProducerResponseDto specs) {
        this.latestUpdate = specs;
        this.latestSnapshot = specs;
    }
}