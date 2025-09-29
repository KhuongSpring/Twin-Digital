package com.example.digital_aggregator_service.service;

import com.example.digital_aggregator_service.domain.dto.response.AggregatorSpecResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface AggregatorService {
    AggregatorSpecResponseDto getSpec(String modelName);
}
