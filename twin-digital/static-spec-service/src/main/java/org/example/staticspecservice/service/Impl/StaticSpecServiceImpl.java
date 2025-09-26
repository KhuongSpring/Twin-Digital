package org.example.staticspecservice.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.example.staticspecservice.constant.ErrorMessage;
import org.example.staticspecservice.domain.dto.request.CarModelImportRequestDto;
import org.example.staticspecservice.domain.dto.response.StaticParameterResponseDto;
import org.example.staticspecservice.domain.dto.response.StaticSpecGroupResponseDto;
import org.example.staticspecservice.domain.entity.StaticSpecGroup;
import org.example.staticspecservice.domain.entity.StaticSpecParameter;
import org.example.staticspecservice.repository.StaticSpecGroupRepository;
import org.example.staticspecservice.repository.StaticSpecParameterRepository;
import org.example.staticspecservice.service.StaticSpecService;
import org.example.staticspecservice.util.CarSpecFileReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaticSpecServiceImpl implements StaticSpecService {

    StaticSpecParameterRepository parameterRepository;

    StaticSpecGroupRepository groupRepository;

    CarSpecFileReader carSpecFileReader;

    private static final Map<String, String> FIELD_TO_GROUP_MAP = new HashMap<>();

    @Override
    @Transactional
    public void importCarSpecification(CarModelImportRequestDto request) {
        try {
            Map<String, Object> specData = carSpecFileReader.readCarSpecFromFile(request.getCarModelName());

            List<StaticSpecParameter> parametersToUpdate = new ArrayList<>();

            for (Map.Entry<String, Object> entry : specData.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();

                if (value == null) {
                    continue;
                }

                String groupName = FIELD_TO_GROUP_MAP.get(fieldName);
                if (groupName == null) {
                    continue;
                }

                StaticSpecParameter existingParameter = parameterRepository.findByParamName(fieldName)
                        .orElseThrow(() -> new RuntimeException(ErrorMessage.StaticSpec.ERR_PARAMETER_NOT_FOUND));

                updateParameterValue(existingParameter, value);
                parametersToUpdate.add(existingParameter);
            }

            parameterRepository.saveAll(parametersToUpdate);
        } catch (Exception e) {
            log.error("Error during car specification import", e);
            throw new RuntimeException(ErrorMessage.StaticSpec.ERR_IMPORT_CAR_SPEC_FAIL);
        }
    }

    private void updateParameterValue(StaticSpecParameter parameter, Object value) {
        if (value instanceof String) {
            parameter.setStringValue((String) value);
            parameter.setDoubleValue(null);
            parameter.setBooleanValue(null);
        } else if (value instanceof Double) {
            parameter.setDoubleValue((Double) value);
            parameter.setStringValue(null);
            parameter.setBooleanValue(null);
        } else if (value instanceof Boolean) {
            parameter.setBooleanValue((Boolean) value);
            parameter.setStringValue(null);
            parameter.setDoubleValue(null);
        }
    }

    @Override
    public List<StaticSpecGroupResponseDto> getAllStaticSpecGroups() {
        try {

            List<StaticSpecGroup> groups = groupRepository.findAll();

            if (groups.isEmpty()) {
                throw new RuntimeException(ErrorMessage.StaticSpec.ERR_NO_GROUPS_FOUND);
            }

            List<StaticSpecGroupResponseDto> responseDto = groups.stream()
                    .map(this::mapToGroupResponseDto)
                    .toList();

            return responseDto;

        } catch (Exception e) {
            log.error("Error fetching static spec groups", e);
            throw new RuntimeException(ErrorMessage.StaticSpec.ERR_GET_ALL_GROUPS_FAIL);
        }
    }

    private StaticSpecGroupResponseDto mapToGroupResponseDto(StaticSpecGroup group) {
        List<StaticParameterResponseDto> parameterDtos = group.getParameterList().stream()
                .map(this::mapToParameterResponseDto)
                .toList();

        return StaticSpecGroupResponseDto.builder()
                .id(group.getId())
                .groupName(group.getGroupName())
                .parameters(parameterDtos)
                .build();
    }


    private StaticParameterResponseDto mapToParameterResponseDto(StaticSpecParameter parameter) {
        return StaticParameterResponseDto.builder()
                .id(parameter.getId())
                .parameterName(parameter.getParamName())
                .stringValue(parameter.getStringValue())
                .doubleValue(parameter.getDoubleValue())
                .booleanValue(parameter.getBooleanValue())
                .valueType(parameter.getValueType().name())
                .groupName(parameter.getGroup().getGroupName())
                .build();
    }

    @Override
    public void resetStaticSpecParameters() {
        try {
            List<StaticSpecParameter> parameters = parameterRepository.findAll();

            if (parameters.isEmpty()) {
                return;
            }

            for (StaticSpecParameter parameter : parameters) {

                parameter.setStringValue(null);
                parameter.setDoubleValue(null);
                parameter.setBooleanValue(null);
            }

            parameterRepository.saveAll(parameters);

        } catch (Exception e) {
            log.error("Error reset static spec param");
            throw new RuntimeException(ErrorMessage.StaticSpec.ERR_RESET_PARAMETERS_FAIL);
        }
    }

    static {
        FIELD_TO_GROUP_MAP.put("modelName", "General");
        FIELD_TO_GROUP_MAP.put("variant", "General");
        FIELD_TO_GROUP_MAP.put("batteryCapacity", "General");
        FIELD_TO_GROUP_MAP.put("driveType", "General");
        FIELD_TO_GROUP_MAP.put("motorType", "General");

        // Dimension
        FIELD_TO_GROUP_MAP.put("wheelbase", "Dimension");
        FIELD_TO_GROUP_MAP.put("length", "Dimension");
        FIELD_TO_GROUP_MAP.put("width", "Dimension");
        FIELD_TO_GROUP_MAP.put("height", "Dimension");
        FIELD_TO_GROUP_MAP.put("groundClearance", "Dimension");
        FIELD_TO_GROUP_MAP.put("turningCircle", "Dimension");
        FIELD_TO_GROUP_MAP.put("trunkCapacitySeatUp", "Dimension");
        FIELD_TO_GROUP_MAP.put("trunkCapacitySeatDown", "Dimension");
        FIELD_TO_GROUP_MAP.put("frunkCapacity", "Dimension");

        // Weight
        FIELD_TO_GROUP_MAP.put("curbWeight", "Weight");
        FIELD_TO_GROUP_MAP.put("payload", "Weight");
        FIELD_TO_GROUP_MAP.put("roofLoad", "Weight");
        FIELD_TO_GROUP_MAP.put("towingCapacityUnbraked", "Weight");
        FIELD_TO_GROUP_MAP.put("towingCapacityBraked", "Weight");

        // Powertrain
        FIELD_TO_GROUP_MAP.put("maxPower", "Powertrain");
        FIELD_TO_GROUP_MAP.put("maxTorque", "Powertrain");
        FIELD_TO_GROUP_MAP.put("maxSpeed", "Powertrain");
        FIELD_TO_GROUP_MAP.put("acceleration_0_100", "Powertrain");
        FIELD_TO_GROUP_MAP.put("rangeEPA", "Powertrain");
        FIELD_TO_GROUP_MAP.put("chargingTimeFast", "Powertrain");
        FIELD_TO_GROUP_MAP.put("chargingStandard", "Powertrain");
        FIELD_TO_GROUP_MAP.put("portableCharger", "Powertrain");

        // Chassis
        FIELD_TO_GROUP_MAP.put("frontSuspension", "Chassis");
        FIELD_TO_GROUP_MAP.put("rearSuspension", "Chassis");
        FIELD_TO_GROUP_MAP.put("brakes", "Chassis");
        FIELD_TO_GROUP_MAP.put("steeringAssist", "Chassis");
        FIELD_TO_GROUP_MAP.put("wheelSize", "Chassis");
        FIELD_TO_GROUP_MAP.put("tireType", "Chassis");
        FIELD_TO_GROUP_MAP.put("spareWheel", "Chassis");
        FIELD_TO_GROUP_MAP.put("inflatorKit", "Chassis");

        // Exterior
        FIELD_TO_GROUP_MAP.put("headlamp", "Exterior");
        FIELD_TO_GROUP_MAP.put("drl", "Exterior");
        FIELD_TO_GROUP_MAP.put("fogLamp", "Exterior");
        FIELD_TO_GROUP_MAP.put("corneringLight", "Exterior");
        FIELD_TO_GROUP_MAP.put("welcomeLight", "Exterior");
        FIELD_TO_GROUP_MAP.put("signatureLights", "Exterior");
        FIELD_TO_GROUP_MAP.put("doorHandle", "Exterior");
        FIELD_TO_GROUP_MAP.put("doorLatch", "Exterior");
        FIELD_TO_GROUP_MAP.put("windows", "Exterior");
        FIELD_TO_GROUP_MAP.put("liftgate", "Exterior");
        FIELD_TO_GROUP_MAP.put("rearGlassHeated", "Exterior");
        FIELD_TO_GROUP_MAP.put("roofType", "Exterior");

        // Interior
        FIELD_TO_GROUP_MAP.put("seatCapacity", "Interior");
        FIELD_TO_GROUP_MAP.put("seatMaterial", "Interior");
        FIELD_TO_GROUP_MAP.put("driverSeat", "Interior");
        FIELD_TO_GROUP_MAP.put("passengerSeat", "Interior");
        FIELD_TO_GROUP_MAP.put("secondRowSeat", "Interior");
        FIELD_TO_GROUP_MAP.put("thirdRowSeat", "Interior");
        FIELD_TO_GROUP_MAP.put("captainChair", "Interior");
        FIELD_TO_GROUP_MAP.put("steeringWheel", "Interior");
        FIELD_TO_GROUP_MAP.put("infotainmentScreen", "Interior");
        FIELD_TO_GROUP_MAP.put("rearScreen", "Interior");
        FIELD_TO_GROUP_MAP.put("hud", "Interior");
        FIELD_TO_GROUP_MAP.put("soundSystem", "Interior");
        FIELD_TO_GROUP_MAP.put("ambientLighting", "Interior");
        FIELD_TO_GROUP_MAP.put("wirelessCharger", "Interior");
        FIELD_TO_GROUP_MAP.put("acType", "Interior");
        FIELD_TO_GROUP_MAP.put("sunBlinds", "Interior");

        // Safety
        FIELD_TO_GROUP_MAP.put("airbags", "Safety");
        FIELD_TO_GROUP_MAP.put("isofix", "Safety");
        FIELD_TO_GROUP_MAP.put("seatbeltPretensioners", "Safety");
        FIELD_TO_GROUP_MAP.put("tpms", "Safety");
        FIELD_TO_GROUP_MAP.put("immobilizer", "Safety");
        FIELD_TO_GROUP_MAP.put("theftAlarm", "Safety");
        FIELD_TO_GROUP_MAP.put("avas", "Safety");

        // ADAS
        FIELD_TO_GROUP_MAP.put("laneAssist", "ADAS");
        FIELD_TO_GROUP_MAP.put("laneDepartureWarning", "ADAS");
        FIELD_TO_GROUP_MAP.put("laneCenteringAssist", "ADAS");
        FIELD_TO_GROUP_MAP.put("trafficJamAssist", "ADAS");
        FIELD_TO_GROUP_MAP.put("highwayAssist", "ADAS");
        FIELD_TO_GROUP_MAP.put("adaptiveCruiseControl", "ADAS");
        FIELD_TO_GROUP_MAP.put("intelligentSpeedAdaptation", "ADAS");
        FIELD_TO_GROUP_MAP.put("trafficSignRecognition", "ADAS");
        FIELD_TO_GROUP_MAP.put("forwardCollisionWarning", "ADAS");
        FIELD_TO_GROUP_MAP.put("rearCrossTrafficAlert", "ADAS");
        FIELD_TO_GROUP_MAP.put("blindSpotDetection", "ADAS");
        FIELD_TO_GROUP_MAP.put("doorOpenWarning", "ADAS");
        FIELD_TO_GROUP_MAP.put("autoEmergencyBrake", "ADAS");
        FIELD_TO_GROUP_MAP.put("emergencyLaneKeepAssist", "ADAS");
        FIELD_TO_GROUP_MAP.put("parkingAssist", "ADAS");
        FIELD_TO_GROUP_MAP.put("surroundViewCamera", "ADAS");
        FIELD_TO_GROUP_MAP.put("autoHighBeam", "ADAS");

        // Connectivity
        FIELD_TO_GROUP_MAP.put("vfStandardConnect", "Connectivity");
        FIELD_TO_GROUP_MAP.put("vfPrimeConnect", "Connectivity");
        FIELD_TO_GROUP_MAP.put("otaUpdate", "Connectivity");
        FIELD_TO_GROUP_MAP.put("appleCarPlay", "Connectivity");
        FIELD_TO_GROUP_MAP.put("androidAuto", "Connectivity");
        FIELD_TO_GROUP_MAP.put("virtualAssistant", "Connectivity");
        FIELD_TO_GROUP_MAP.put("smartHomeControl", "Connectivity");
        FIELD_TO_GROUP_MAP.put("videoStreaming", "Connectivity");
        FIELD_TO_GROUP_MAP.put("games", "Connectivity");
        FIELD_TO_GROUP_MAP.put("webBrowser", "Connectivity");
        FIELD_TO_GROUP_MAP.put("eSIM", "Connectivity");
    }
}
