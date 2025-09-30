package org.example.staticspecservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.staticspecservice.base.RestApiV1;
import org.example.staticspecservice.base.VsResponseUtil;
import org.example.staticspecservice.constant.SuccessMessage;
import org.example.staticspecservice.constant.UrlConstant;
import org.example.staticspecservice.domain.dto.request.CarModelImportRequestDto;
import org.example.staticspecservice.domain.dto.response.StaticSpecGroupResponseDto;
import org.example.staticspecservice.service.StaticSpecService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "static-spec-controller", description = "Static Specification Management APIs")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaticSpecController {

    StaticSpecService staticSpecService;

    @Operation(
            summary = "Import thông số tĩnh",
            description = "Dùng để nhập thông số tĩnh của xe vào hệ thống"
    )
    @PostMapping(value = UrlConstant.StaticSpec.IMPORT_CAR_SPECIFICATION)
    public ResponseEntity<?> importCarSpecification(@RequestBody CarModelImportRequestDto request) {
        staticSpecService.importCarSpecification(request);
        return VsResponseUtil.success(SuccessMessage.StaticSpec.IMPORT_CAR_SPEC_SUCCESS);
    }

    @Operation(
            summary = "Lấy tất cả nhóm thông số tĩnh",
            description = "Dùng để lấy tất cả nhóm thông số tĩnh cùng với các tham số của từng nhóm"
    )
    @GetMapping(value = UrlConstant.StaticSpec.GET_ALL_GROUPS)
    public ResponseEntity<?> getAllStaticSpecGroups() {
        List<StaticSpecGroupResponseDto> groups = staticSpecService.getAllStaticSpecGroups();
        return VsResponseUtil.success(groups);
    }

    @Operation(
            summary = "Reset thông số tĩnh về trạng thái ban đầu",
            description = "Dùng để reset các thông số tĩnh về trạng thái ban đầu"
    )
    @PostMapping(value = UrlConstant.StaticSpec.RESET_PARAMETERS)
    public ResponseEntity<?> resetStaticSpecParameters() {
        staticSpecService.resetStaticSpecParameters();
        return VsResponseUtil.success(SuccessMessage.StaticSpec.RESET_PARAMETERS_SUCCESS);
    }
}
