package org.example.carservice.service;

import org.example.carservice.domain.dto.request.EnterCarRequestDto;
import org.example.carservice.domain.dto.response.EnterCarResponseDto;

public interface CarService {

  EnterCarResponseDto enterCar(EnterCarRequestDto request);
  
  EnterCarResponseDto getCarByUserId(String userId);
}
