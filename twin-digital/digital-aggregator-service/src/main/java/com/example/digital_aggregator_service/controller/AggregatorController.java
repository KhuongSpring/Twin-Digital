package com.example.digital_aggregator_service.controller;

import com.example.digital_aggregator_service.base.RestApiV1;
import com.example.digital_aggregator_service.base.VsResponseUtil;
import com.example.digital_aggregator_service.constant.UrlConstant;
import com.example.digital_aggregator_service.domain.dto.response.AggregatorSpecResponseDto;
import com.example.digital_aggregator_service.service.AggregatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Digital Aggregator", description = "API for aggregating static and dynamic specifications")
public class AggregatorController {

    AggregatorService aggregatorService;

    @Operation(
            summary = "Get specifications by model name",
            description = "Retrieves both static and dynamic specifications"
    )
    @GetMapping(UrlConstant.DigitalAggregator.GET_SPECIFICATION)
    public ResponseEntity<?> getSpec(@RequestParam String modelName) {
        return VsResponseUtil.success(aggregatorService.getSpec(modelName));
    }
}
