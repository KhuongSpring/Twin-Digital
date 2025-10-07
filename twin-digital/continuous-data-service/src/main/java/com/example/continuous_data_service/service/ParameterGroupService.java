package com.example.continuous_data_service.service;

import com.example.continuous_data_service.domain.entity.ParameterGroup;
import com.example.continuous_data_service.domain.entity.ParameterGroupSnapshot;
import org.springframework.stereotype.Service;

@Service
public interface ParameterGroupService {
    ParameterGroup getGroup();

    void updateParameter(String paramName, Object value);

    ParameterGroupSnapshot getSnapshot();

    void applyLogic();
}
