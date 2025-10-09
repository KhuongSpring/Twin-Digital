package com.example.continuous_data_service.store;

import com.example.continuous_data_service.domain.entity.DynamicParameter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicParameterStore {

    private final Map<String, DynamicParameter> store = new ConcurrentHashMap<>();

    public void setAll(Map<String, DynamicParameter> parameters) {
        store.clear();
        store.putAll(parameters);
    }

    public Map<String, DynamicParameter> getAll() {
        return store;
    }

    public DynamicParameter get(String name) {
        return store.get(name);
    }

    public void update(String name, Object newValue) {
        DynamicParameter param = store.get(name);
        if (param != null) {
            param.setValue(newValue);
        }
    }
}