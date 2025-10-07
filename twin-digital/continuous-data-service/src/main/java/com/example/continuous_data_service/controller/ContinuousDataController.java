package com.example.continuous_data_service.controller;

import com.example.continuous_data_service.base.RestApiV1;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@RestApiV1
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContinuousDataController {
}
