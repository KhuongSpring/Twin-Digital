package com.example.dynamic_spec_service.controller;

import com.example.dynamic_spec_service.base.RestApiV1;
import com.example.dynamic_spec_service.base.VsResponseUtil;
import com.example.dynamic_spec_service.constant.SuccessMessage;
import com.example.dynamic_spec_service.constant.UrlConstant;
import com.example.dynamic_spec_service.domain.dto.request.DynamicParameterEnterRequestDto;
import com.example.dynamic_spec_service.domain.dto.request.ResetDynamicSpecRequestDto;
import com.example.dynamic_spec_service.service.DynamicSpecService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@Validated
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DynamicSpecController {

    DynamicSpecService dynamicSpecService;

    @Operation(
            summary = "Điền thông số động cho xe",
            description = "Dùng để điền thông số động cho xe"
    )
    @PostMapping(UrlConstant.DynamicSpec.ENTER_SPEC)
    public ResponseEntity<?> enterSpec(@Valid @RequestBody DynamicParameterEnterRequestDto request) throws IllegalAccessException {
        return VsResponseUtil.success(dynamicSpecService.enterSpec(request));
    }

    @Operation(
            summary = "Lấy thông số động của xe",
            description = "Dùng để lấy thông số động của xe"
    )
    @GetMapping(UrlConstant.DynamicSpec.SHOW_SPEC)
    public ResponseEntity<?> getSpec() {
        return VsResponseUtil.success(dynamicSpecService.getSpec());
    }

    @Operation(
            summary = "Đặt lại thông số xe",
            description = "Dùng để đặt lại thông số xe"
    )
    @PostMapping(UrlConstant.DynamicSpec.RESET_SPEC)
    public ResponseEntity<?> resetSpec(@Valid @RequestBody ResetDynamicSpecRequestDto request) {
        dynamicSpecService.resetSpec(request);
        return VsResponseUtil.success(SuccessMessage.DynamicSpec.RESET_SPEC_SUCCESS);
    }

    @Operation(
            summary = "Thiết lập thông số xe",
            description = "Dùng để thiết lập giá trị mặc định của thông số xe"
    )
    @PostMapping(UrlConstant.DynamicSpec.INIT_SPEC)
    public ResponseEntity<?> initSpec() {
        return VsResponseUtil.success(dynamicSpecService.initParameterData());
    }

}
