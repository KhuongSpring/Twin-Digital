package com.example.dynamic_spec_service.util;

import com.example.dynamic_spec_service.domain.dto.seed_data.SpecSeedDto;
import com.example.dynamic_spec_service.domain.entity.DynamicParameter;
import com.example.dynamic_spec_service.domain.entity.DynamicSpecGroup;
import com.example.dynamic_spec_service.domain.entity.ValueType;
import com.example.dynamic_spec_service.repository.DynamicSpecGroupRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final DynamicSpecGroupRepository groupRepository;

    @Bean
    CommandLineRunner seedDatabase(ObjectMapper objectMapper) {
        return args -> {
            try (InputStream inputStream =
                         getClass().getResourceAsStream("/data/spec-seed.json")) {

                if (groupRepository.count() != 0) return;

                List<SpecSeedDto> seedGroups = objectMapper.readValue(
                        inputStream, new TypeReference<>() {}
                );

                for (SpecSeedDto dto : seedGroups) {
                    DynamicSpecGroup group = new DynamicSpecGroup();
                    group.setGroupName(dto.getGroupName());

                    List<DynamicParameter> params = dto.getParameters().stream()
                            .map(p -> DynamicParameter.builder()
                                    .paramName(p.getParamName())
                                    .valueType(ValueType.valueOf(p.getValueType()))
                                    .group(group)
                                    .build())
                            .toList();

                    group.setParameters(params);

                    groupRepository.save(group);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
