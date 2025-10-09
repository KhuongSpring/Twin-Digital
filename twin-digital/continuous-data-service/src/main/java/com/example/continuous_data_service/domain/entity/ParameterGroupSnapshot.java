package com.example.continuous_data_service.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParameterGroupSnapshot {

    ParameterGroup group;

    Map<String, ParameterSnapshot> parameters;

    Instant lastUpdated;
}
