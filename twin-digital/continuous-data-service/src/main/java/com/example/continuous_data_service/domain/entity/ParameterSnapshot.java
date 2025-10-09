package com.example.continuous_data_service.domain.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE)
public class ParameterSnapshot {

    String paramName;

    Object value;

    Instant lastUpdated;
}
