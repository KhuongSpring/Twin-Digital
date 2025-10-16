package com.example.continuous_data_service.service;

import com.example.continuous_data_service.domain.dto.request.ManualDataUpdateRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface ManualDataService {
    void updateManualData (ManualDataUpdateRequestDto request);
}
