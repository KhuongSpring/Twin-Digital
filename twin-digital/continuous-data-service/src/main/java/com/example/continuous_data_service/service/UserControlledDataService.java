package com.example.continuous_data_service.service;

import com.example.continuous_data_service.domain.dto.request.UserControlledDataUpdateRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface UserControlledDataService {
    void updateUserControlledData(UserControlledDataUpdateRequestDto request);
}
