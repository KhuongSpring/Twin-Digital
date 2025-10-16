package com.example.continuous_data_service.domain.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManualDataUpdateRequestDto {

    List<Map<String, Object>> data;

}
