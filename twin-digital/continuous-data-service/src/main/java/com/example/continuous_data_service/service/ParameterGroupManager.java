package com.example.continuous_data_service.service;

import com.example.continuous_data_service.domain.entity.ParameterGroup;
import com.example.continuous_data_service.domain.entity.ParameterGroupSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ParameterGroupManager {

    private final Map<ParameterGroup, ParameterGroupService> services = new EnumMap<>(ParameterGroup.class);

    public ParameterGroupManager(List<ParameterGroupService> serviceList) {
        for (ParameterGroupService service : serviceList) {
            services.put(service.getGroup(), service);
        }
    }

    public void updateParameter(String key, Object value, ParameterGroup group) {
        ParameterGroupService service = services.get(group);
        if (service != null) {
            service.updateParameter(key, value);
        }
    }

    public ParameterGroupSnapshot getGroupSnapshot(ParameterGroup group) {
        return services.get(group).getSnapshot();
    }

    public List<ParameterGroupSnapshot> getAllSnapshots() {
        return services.values().stream()
                .map(ParameterGroupService::getSnapshot)
                .toList();
    }
}

