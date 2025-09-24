package com.example.dynamic_spec_service.domain.dto.seed_data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SpecSeedDto {
    String groupName;
    List<ParamSeedDto> parameters;
}
