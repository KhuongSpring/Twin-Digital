package com.example.continuous_data_service.config;

import com.example.continuous_data_service.domain.dto.response.DynamicSpecProducerResponseDto;
import com.example.continuous_data_service.domain.entity.DynamicParameter;
import com.example.continuous_data_service.store.DynamicParameterStore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Getter
@RequiredArgsConstructor
public class KafkaConsumeConfig {

    private final DynamicParameterStore dynamicParameterStore;

    private volatile DynamicSpecProducerResponseDto initData =
            new DynamicSpecProducerResponseDto();

    @KafkaListener(
            topics = "dynamic-spec-update-topic",
            groupId = "continuous-service-group",
            containerFactory = "dynamicSpecKafkaListenerContainerFactory"
    )
    public void listenInitData(DynamicSpecProducerResponseDto specs) {
        this.initData = specs;

        Map<String, DynamicParameter> paramMap = specs.getSpecs().stream()
                .flatMap(group -> group.getParameters().stream()
                        .map(param -> new DynamicParameter(
                                param.getParamName(),
                                param.getNumericValue() != null ? param.getNumericValue() : param.getStringValue(),
                                param.getValueType(),
                                group.getGroupName()
                        )))
                .collect(Collectors.toMap(
                        DynamicParameter::getName,
                        p -> p
                ));

        dynamicParameterStore.setAll(paramMap);

        System.out.println("[Continuous-Service] Stored "
                + paramMap.size() + " dynamic parameters from aggregator");

        dynamicParameterStore.getAll().forEach((key, value) -> {
            System.out.println(key + " " + value);
        });
    }
}