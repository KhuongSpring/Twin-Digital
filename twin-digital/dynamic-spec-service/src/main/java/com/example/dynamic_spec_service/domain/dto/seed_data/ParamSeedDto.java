package com.example.dynamic_spec_service.domain.dto.seed_data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParamSeedDto {
    String paramName;
    String valueType;
}
