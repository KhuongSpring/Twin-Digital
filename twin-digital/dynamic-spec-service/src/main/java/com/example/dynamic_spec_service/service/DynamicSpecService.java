package com.example.dynamic_spec_service.service;

import com.example.dynamic_spec_service.domain.dto.request.DynamicParameterEnterRequestDto;
import com.example.dynamic_spec_service.domain.dto.request.ResetDynamicSpecRequestDto;
import com.example.dynamic_spec_service.domain.dto.response.DynamicSpecGroupResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DynamicSpecService {
        List<DynamicSpecGroupResponseDto> enterSpec(DynamicParameterEnterRequestDto request) throws IllegalAccessException;

        List<DynamicSpecGroupResponseDto> getSpec();

        void resetSpec(ResetDynamicSpecRequestDto idRequest);
}
