package org.example.staticspecservice.domain.mapper;

import org.example.staticspecservice.domain.dto.response.StaticSpecGroupResponseDto;
import org.example.staticspecservice.domain.entity.StaticSpecGroup;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StaticSpecMapper {

    List<StaticSpecGroupResponseDto> toGroupDtoList(List<StaticSpecGroup> entities);

}
