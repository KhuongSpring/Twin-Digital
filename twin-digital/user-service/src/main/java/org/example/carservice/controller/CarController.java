package org.example.carservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.carservice.base.RestApiV1;
import org.example.carservice.base.VsResponseUtil;
import org.example.carservice.constant.UrlConstant;
import org.example.carservice.domain.dto.request.EnterCarRequestDto;
import org.example.carservice.domain.dto.response.EnterCarResponseDto;
import org.example.carservice.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@Validated
@RequiredArgsConstructor
@Tag(name = "car-controller", description = "Car Management APIs")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarController {

  CarService carService;

  @Operation(summary = "Đưa xe vào hệ thống", description = "API để đưa xe điện vào hệ thống Digital Twin, bao gồm việc import thông số tĩnh và khởi tạo thông số động")
  @PostMapping(value = UrlConstant.Car.ENTER_CAR)
  public ResponseEntity<?> enterCar(@Valid @RequestBody EnterCarRequestDto request) {
    EnterCarResponseDto response = carService.enterCar(request);
    return VsResponseUtil.success(response);
  }
}
