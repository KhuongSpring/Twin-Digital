package com.example.digital_aggregator_service.controller;

import com.example.digital_aggregator_service.base.RestApiV1;
import com.example.digital_aggregator_service.base.VsResponseUtil;
import com.example.digital_aggregator_service.constant.UrlConstant;
import com.example.digital_aggregator_service.service.AggregatorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiV1
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AggregatorController {

    AggregatorService aggregatorService;

    @GetMapping(UrlConstant.DigitalAggregator.GET_SPECIFICATION)
    public ResponseEntity<?> getSpec() {
        String modelName = "";
        return VsResponseUtil.success(aggregatorService.getSpec(modelName));
    }
}
