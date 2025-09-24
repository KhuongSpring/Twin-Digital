package org.example.staticspecservice.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.staticspecservice.domain.dto.help.ParamSeedDto;
import org.example.staticspecservice.domain.dto.help.SpecSeedDto;
import org.example.staticspecservice.domain.entity.StaticSpecGroup;
import org.example.staticspecservice.domain.entity.StaticSpecParameter;
import org.example.staticspecservice.domain.entity.ValueType;
import org.example.staticspecservice.repository.StaticSpecGroupRepository;
import org.example.staticspecservice.repository.StaticSpecParameterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitialize implements CommandLineRunner {

    private final StaticSpecGroupRepository groupRepository;
    private final StaticSpecParameterRepository parameterRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (groupRepository.count() > 0) {
            log.info("Data already exists, skip init");
            return;
        }
        try {
            initializeStaticSpecData();
            log.info("Data initialization completed successfully");
        } catch (Exception e) {
            log.error("Error during data initialization", e);
            throw e;
        }
    }

    private void initializeStaticSpecData() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/static-spec.json");
        try (InputStream inputStream = resource.getInputStream()) {
            List<SpecSeedDto> specSeedList = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<SpecSeedDto>>() {
                    });
            for (SpecSeedDto specSeed : specSeedList) {
                processGroup(specSeed);
            }
        }
    }

    private void processGroup(SpecSeedDto specSeed) {
        log.debug("Processing group: {}", specSeed.getGroupName());

        StaticSpecGroup group = StaticSpecGroup.builder()
                .groupName(specSeed.getGroupName())
                .parameterList(new ArrayList<>())
                .build();

        StaticSpecGroup savedGroup = groupRepository.save(group);
        log.debug("Saved group: {} with ID: {}", savedGroup.getGroupName(), savedGroup.getId());

        List<StaticSpecParameter> parameters = new ArrayList<>();
        for (ParamSeedDto paramSeed : specSeed.getParameters()) {
            StaticSpecParameter parameter = createParameter(paramSeed, savedGroup);
            parameters.add(parameter);
        }

        parameterRepository.saveAll(parameters);
        log.debug("Saved {} parameters for group: {}", parameters.size(), savedGroup.getGroupName());
    }

    private StaticSpecParameter createParameter(ParamSeedDto paramSeed, StaticSpecGroup group) {
        ValueType valueType = ValueType.valueOf(paramSeed.getValueType());

        StaticSpecParameter.StaticSpecParameterBuilder builder = StaticSpecParameter.builder()
                .paramName(paramSeed.getParamName())
                .valueType(valueType)
                .group(group);

        switch (valueType) {
            case STRING:
                builder.stringValue((String) paramSeed.getValue());
                break;
            case NUMBER:
                Object numberValue = paramSeed.getValue();
                if (numberValue instanceof Number) {
                    builder.doubleValue(((Number) numberValue).doubleValue());
                } else if (numberValue == null) {
                    builder.doubleValue(0.0);
                }
                break;
            case BOOLEAN:
                Object booleanValue = paramSeed.getValue();
                if (booleanValue instanceof Boolean) {
                    builder.booleanValue((Boolean) booleanValue);
                } else if (booleanValue == null) {
                    builder.booleanValue(false);
                }
                break;
        }

        return builder.build();
    }
}
