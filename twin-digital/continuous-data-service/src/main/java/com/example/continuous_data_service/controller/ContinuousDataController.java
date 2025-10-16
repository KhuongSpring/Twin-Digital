package com.example.continuous_data_service.controller;

import com.example.continuous_data_service.base.RestApiV1;
import com.example.continuous_data_service.base.VsResponseUtil;
import com.example.continuous_data_service.constant.SuccessMessage;
import com.example.continuous_data_service.constant.UrlConstant;
import com.example.continuous_data_service.domain.dto.request.ManualDataUpdateRequestDto;
import com.example.continuous_data_service.service.ManualDataService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContinuousDataController {

    ManualDataService manualDataService;

    @Operation(
            summary = "Điền thông số động dạng manual cho xe",
            description = "Dùng để điền thông số động dạng manual cho xe"
    )
    @PostMapping(UrlConstant.ContinuesData.UPDATE_MANUAL_DATA)
    public ResponseEntity<?> updateManualData(@Valid @RequestBody ManualDataUpdateRequestDto request) {
        manualDataService.updateManualData(request);
        return VsResponseUtil.success(SuccessMessage.ManualData.UPDATE_MANUAL_SUCCESS);
    }
}
