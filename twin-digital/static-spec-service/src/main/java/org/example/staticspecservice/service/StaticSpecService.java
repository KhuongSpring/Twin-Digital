package org.example.staticspecservice.service;

import org.example.staticspecservice.domain.dto.request.ResetStaticSpecRequestDto;
import org.example.staticspecservice.domain.dto.request.StaticParameterImportRequestDto;
import org.example.staticspecservice.domain.dto.response.StaticSpecGroupResponseDto;

import java.util.List;

public interface StaticSpecService {

    void importCarSpecification(StaticParameterImportRequestDto request);

    List<StaticSpecGroupResponseDto> getAllStaticSpecGroups();

    void resetStaticSpecParameters(ResetStaticSpecRequestDto request);
}
