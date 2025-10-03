package com.example.digital_aggregator_service.config;

import com.example.digital_aggregator_service.domain.dto.response.DynamicSpecProducerResponseDto;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Service
public class DynamicSpecConsumer {

    private volatile DynamicSpecProducerResponseDto latestSnapshot =
            new DynamicSpecProducerResponseDto(Collections.emptyList());

    private volatile DynamicSpecProducerResponseDto latestUpdate =
            new DynamicSpecProducerResponseDto(Collections.emptyList());

    @KafkaListener(
            topics = "dynamic-spec-snapshot-topic",
            groupId = "aggregator-group",
            containerFactory = "dynamicSpecKafkaListenerContainerFactory"
    )
    public void listenSnapshot(DynamicSpecProducerResponseDto specs) {
        this.latestSnapshot = specs;
    }

    @KafkaListener(
            topics = "dynamic-spec-update-topic",
            groupId = "aggregator-group",
            containerFactory = "dynamicSpecKafkaListenerContainerFactory"
    )
    public void listenUpdate(DynamicSpecProducerResponseDto specs) {
        this.latestUpdate = specs;
        this.latestSnapshot = specs;
    }

}
