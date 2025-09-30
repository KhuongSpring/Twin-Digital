package com.example.dynamic_spec_service.service.impl;

import com.example.dynamic_spec_service.domain.dto.request.DynamicParameterEnterRequestDto;
import com.example.dynamic_spec_service.domain.dto.request.ResetDynamicSpecRequestDto;
import com.example.dynamic_spec_service.domain.dto.response.DynamicSpecGroupResponseDto;
import com.example.dynamic_spec_service.domain.dto.response.DynamicSpecProducerResponseDto;
import com.example.dynamic_spec_service.domain.entity.DynamicParameter;
import com.example.dynamic_spec_service.domain.entity.ValueType;
import com.example.dynamic_spec_service.domain.mapper.DynamicSpecMapper;
import com.example.dynamic_spec_service.repository.DynamicParameterRepository;
import com.example.dynamic_spec_service.repository.DynamicSpecGroupRepository;
import com.example.dynamic_spec_service.service.DynamicSpecService;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DynamicSpecServiceImpl implements DynamicSpecService {

    DynamicParameterRepository dynamicParameterRepository;

    DynamicSpecGroupRepository dynamicSpecGroupRepository;

    DynamicSpecMapper dynamicSpecMapper;

    KafkaTemplate<String, DynamicSpecProducerResponseDto> kafkaTemplate;

    @Override
    public List<DynamicSpecGroupResponseDto> enterSpec(DynamicParameterEnterRequestDto request) throws IllegalAccessException {
        List<Object> values = extractValues(request);
        List<DynamicParameter> parameters = dynamicParameterRepository.findAll(Sort.by("id").ascending());

        for (int i = 0; i < parameters.size(); i++) {
            DynamicParameter param = parameters.get(i);
            Object value = values.get(i);

            if (value == null) continue;

            if (value instanceof Number) {
                param.setNumericValue(((Number) value).doubleValue());
                param.setStringValue(null);
                param.setValueType(ValueType.NUMBER);
            } else {
                param.setStringValue(value.toString());
                param.setNumericValue(null);
                param.setValueType(ValueType.STRING);
            }
        }
        dynamicParameterRepository.saveAll(parameters);

        return dynamicSpecMapper.toGroupDtoList(dynamicSpecGroupRepository.findAll());
    }

    @Override
    public List<DynamicSpecGroupResponseDto> getSpec() {
        List<DynamicSpecGroupResponseDto> specs = dynamicSpecMapper.toGroupDtoList(dynamicSpecGroupRepository.findAll());
        DynamicSpecProducerResponseDto result = new DynamicSpecProducerResponseDto(specs);

        kafkaTemplate.send("dynamic-spec-topic", result);
        return specs;
    }

    @Override
    public void resetSpec(ResetDynamicSpecRequestDto idRequest) {
        List<DynamicParameter> parameters = dynamicParameterRepository.findAll(Sort.by("id").ascending());

        for (Long id : idRequest.getListId()) {
            parameters.get((int) (id - 1)).setStringValue(null);
            parameters.get((int) (id - 1)).setNumericValue(null);

            dynamicParameterRepository.save(parameters.get((int) (id - 1)));
        }
    }

    public List<Object> extractValues(DynamicParameterEnterRequestDto request) {
        return Arrays.asList(
                request.getSpeed(),
                request.getRpm(),
                request.getGear(),
                request.getOdometer(),
                request.getTripDistance(),
                request.getDriveMode(),
                request.getBatteryLevel(),
                request.getChargingStatus(),
                request.getRangeRemaining(),
                request.getBatteryTemperature(),
                request.getChargingPower(),
                request.getChargingTimeRemaining(),
                request.getEnergyConsumption(),
                request.getMotorPower(),
                request.getMotorTemperature(),
                request.getTorqueOutput(),
                request.getCoolantTemperature(),
                request.getOilPressure(),
                request.getBrakeStatus(),
                request.getAbsActive(),
                request.getBrakePadWear(),
                request.getBrakeTemperature(),
                request.getAirbagStatus(),
                request.getTirePressure(),
                request.getTireTemperature(),
                request.getTpmsWarning(),
                request.getGps_latitude(),
                request.getGps_longitude(),
                request.getGps_speed(),
                request.getSignalStrength(),
                request.getGps_altitude(),
                request.getGps_heading(),
                request.getNetworkStatus(),
                request.getOtaStatus(),
                request.getDoorStatus_front_left(),
                request.getDoorStatus_front_right(),
                request.getDoorStatus_rear_left(),
                request.getDoorStatus_rear_right(),
                request.getDoorStatus_trunk(),
                request.getLockStatus(),
                request.getAcStatus(),
                request.getCabinTemperature(),
                request.getSeatHeating(),
                request.getHeadlightStatus(),
                request.getCheckEngine(),
                request.getBatteryFault(),
                request.getBrakeFault(),
                request.getCoolingFault(),
                request.getMotorOverheat(),
                request.getTireLeak()
        );
    }


}
