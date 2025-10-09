package com.example.continuous_data_service.service.impl;

import com.example.continuous_data_service.domain.entity.ParameterGroup;
import com.example.continuous_data_service.domain.entity.ParameterGroupSnapshot;
import com.example.continuous_data_service.domain.entity.ParameterSnapshot;
import com.example.continuous_data_service.service.ParameterGroupService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BatteryGroupService implements ParameterGroupService {

    private final Map<String, ParameterSnapshot> parameters = new ConcurrentHashMap<>();

    @Override
    public ParameterGroup getGroup() {
        return ParameterGroup.BATTERY;
    }

    @Override
    public void updateParameter(String key, Object value) {
        parameters.put(key, new ParameterSnapshot(key, value, Instant.now()));
        applyLogic();
    }

    @Override
    public ParameterGroupSnapshot getSnapshot() {
        return new ParameterGroupSnapshot(getGroup(), new HashMap<>(parameters), Instant.now());
    }

    @Override
    public void applyLogic() {
        Double batteryLevel = getDouble("batteryLevel");
        Double temperature = getDouble("batteryTemperature");
        Boolean fault = getBoolean("batteryFault");

        if (batteryLevel != null && batteryLevel < 10) {
            System.out.println("⚠️ Low battery warning");
        }
        if (temperature != null && temperature > 60) {
            System.out.println("🔥 Battery overheating");
        }
        if (Boolean.TRUE.equals(fault)) {
            System.out.println("❌ Battery fault detected");
        }
    }

    private Double getDouble(String key) {
        ParameterSnapshot snap = parameters.get(key);
        if (snap == null) return null;
        try {
            return Double.parseDouble(snap.getValue().toString());
        } catch (Exception e) {
            return null;
        }
    }

    private Boolean getBoolean(String key) {
        ParameterSnapshot snap = parameters.get(key);
        if (snap == null) return null;
        return Boolean.parseBoolean(snap.getValue().toString());
    }
}
