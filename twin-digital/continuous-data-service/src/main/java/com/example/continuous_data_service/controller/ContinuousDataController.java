package com.example.continuous_data_service.controller;

import com.example.continuous_data_service.base.RestApiV1;
import com.example.continuous_data_service.base.VsResponseUtil;
import com.example.continuous_data_service.constant.SuccessMessage;
import com.example.continuous_data_service.constant.UrlConstant;
import com.example.continuous_data_service.domain.dto.request.UserControlledDataUpdateRequestDto;
import com.example.continuous_data_service.service.UserControlledDataService;
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

    UserControlledDataService userControlledDataService;

    @Operation(
            summary = "Điền thông số động dạng con người điều khiển cho xe",
            description = "Dùng để điền thông số động dạng con người điều khiển cho xe"
    )
    @PostMapping(UrlConstant.ContinuesData.UPDATE_USER_CONTROLLED_DATA)
    public ResponseEntity<?> updateUserControlledData(@Valid @RequestBody UserControlledDataUpdateRequestDto request) {
        userControlledDataService.updateUserControlledData(request);
        return VsResponseUtil.success(SuccessMessage.UserControlledData.UPDATE_USER_CONTROLLED_DATA_SUCCESS);
    }
}
