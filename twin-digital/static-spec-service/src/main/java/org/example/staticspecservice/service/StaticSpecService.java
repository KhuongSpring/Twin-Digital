package org.example.staticspecservice.service;

import org.example.staticspecservice.domain.dto.request.CarModelImportRequestDto;
import org.example.staticspecservice.domain.dto.response.StaticSpecGroupResponseDto;

import java.util.List;

public interface StaticSpecService {

    void importCarSpecification(CarModelImportRequestDto request);

    List<StaticSpecGroupResponseDto> getAllStaticSpecGroups();

    void resetStaticSpecParameters();
}
