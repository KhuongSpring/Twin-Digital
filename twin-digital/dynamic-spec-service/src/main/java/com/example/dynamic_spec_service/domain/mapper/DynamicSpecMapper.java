package com.example.dynamic_spec_service.domain.mapper;

import com.example.dynamic_spec_service.domain.dto.response.DynamicParameterResponseDto;
import com.example.dynamic_spec_service.domain.dto.response.DynamicSpecGroupResponseDto;
import com.example.dynamic_spec_service.domain.entity.DynamicParameter;
import com.example.dynamic_spec_service.domain.entity.DynamicSpecGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DynamicSpecMapper {

    @Mapping(source = "group.groupName", target = "groupName")
    @Mapping(source = "valueType", target = "valueType") // enum -> string
    DynamicParameterResponseDto toDto(DynamicParameter entity);

    @Mapping(source = "groupName", target = "groupName")
    @Mapping(source = "parameters", target = "parameters")
    DynamicSpecGroupResponseDto toDto(DynamicSpecGroup entity);

    List<DynamicParameterResponseDto> toParameterDtoList(List<DynamicParameter> entities);

    List<DynamicSpecGroupResponseDto> toGroupDtoList(List<DynamicSpecGroup> entities);
}
