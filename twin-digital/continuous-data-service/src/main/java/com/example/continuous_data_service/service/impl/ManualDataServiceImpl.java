package com.example.continuous_data_service.service.impl;

import com.example.continuous_data_service.config.DynamicParameterConfig;
import com.example.continuous_data_service.config.DynamicSpecServiceConfig;
import com.example.continuous_data_service.constant.ErrorMessage;
import com.example.continuous_data_service.domain.dto.request.DynamicParameterEnterRequestDto;
import com.example.continuous_data_service.domain.dto.request.ManualDataUpdateRequestDto;
import com.example.continuous_data_service.domain.entity.DynamicParameter;
import com.example.continuous_data_service.exception.VsException;
import com.example.continuous_data_service.service.ManualDataService;
import com.example.continuous_data_service.store.DynamicParameterStore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Field;
import java.util.Map;

@Service
@Getter
@RequiredArgsConstructor
public class ManualDataServiceImpl implements ManualDataService {

    WebClient.Builder webClientBuilder;

    private final DynamicParameterStore dynamicParameterStore;

    DynamicSpecServiceConfig dynamicSpecServiceConfig;

    @Override
    public void updateManualData(ManualDataUpdateRequestDto request) {
        for (Map<String, Object> item : request.getData()) {
            item.forEach((key, value) -> {
                if (value == null)
                    throw new VsException(ErrorMessage.ManualData.ERR_VALUE_NULL);

                if (DynamicParameterConfig.isRealtime(key))
                    throw new VsException(ErrorMessage.ManualData.ERR_IS_REALTIME_DATA);

                handleLogic(key, value);
            });
        }

//        updateManualDataToDynamicSpecService(request);
    }

    private void updateManualDataToDynamicSpecService(ManualDataUpdateRequestDto request) {
        try {
            WebClient webClient = webClientBuilder
                    .baseUrl(dynamicSpecServiceConfig.getUrl())
                    .build();

            Map<String, Object> response = webClient.post()
                    .uri("/dynamic-spec/enter-spec")
                    .bodyValue(convertToEnterRequest(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();

            if (response != null && response.containsKey("data")) {
                System.out.println("Update manual data successfully");
                return;
            }

            throw new VsException("Update manual data from Dynamic Spec Service unsuccessfully");

        } catch (Exception e) {
            throw new VsException("Error calling Dynamic Spec Service: " + e.getMessage());
        }
    }

    private void handleLogic(String paramName, Object value) {
        switch (paramName) {
            case "driveMode": {
                if (value.equals("DRIVE")) {
                    if (dynamicParameterStore.get("chargingStatus").getValue().equals("Charging"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CHARGING);

                    if (dynamicParameterStore.get("doorStatus_front_left").getValue().equals("Opened")
                            || dynamicParameterStore.get("doorStatus_front_right").getValue().equals("Opened")
                            || dynamicParameterStore.get("doorStatus_rear_left").getValue().equals("Opened")
                            || dynamicParameterStore.get("doorStatus_rear_right").getValue().equals("Opened"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_ANY_DOOR_OPENED);

                    if (dynamicParameterStore.get("brakeFault").getValue().equals("True"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_BRAKE_FAULT);

                    if (dynamicParameterStore.get("motorOverheat").getValue().equals("True"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_MOTOR_OVERHEAT);

                    if (dynamicParameterStore.get("batteryFault").getValue().equals("True"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_BATTERY_FAULT);

                } else if (value.equals("PARK")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_RUNNING);
                }
                break;
            }
            case "oilPressure": {
                if (dynamicParameterStore.get("speed").getValue().equals(0)
                        && dynamicParameterStore.get("driveMode").getValue().equals("PARK"))
                    throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_OFF);
                break;
            }
            case "airbagStatus": {
                if (!dynamicParameterStore.get("speed").getValue().equals(0))
                    throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_RUNNING);
                break;
            }
            case "otaStatus": {
                if (!dynamicParameterStore.get("driveMode").getValue().equals("PARK"))
                    throw new VsException(ErrorMessage.ManualData.ERR_IS_PARK);

                if (!dynamicParameterStore.get("speed").getValue().equals(0))
                    throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_RUNNING);
                break;

            }
            case "doorStatus_front_left": {
                if (value.equals("Opened")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_RUNNING);

                    if (dynamicParameterStore.get("lockStatus").getValue().equals("True"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_LOCKED);

                    if (dynamicParameterStore.get("driveMode").getValue().equals("DRIVE"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_MODE_DRIVE);

                    if (dynamicParameterStore.get("driveMode").getValue().equals("REVERSE"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_MODE_REVERSE);
                }
                break;
            }
            case "doorStatus_front_right": {
                if (value.equals("Opened")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_RUNNING);

                    if (dynamicParameterStore.get("lockStatus").getValue().equals("True"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_LOCKED);

                    if (dynamicParameterStore.get("driveMode").getValue().equals("DRIVE"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_MODE_DRIVE);

                    if (dynamicParameterStore.get("driveMode").getValue().equals("REVERSE"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_MODE_REVERSE);
                }
                break;
            }
            case "doorStatus_rear_left": {
                if (value.equals("Opened")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_RUNNING);

                    if (dynamicParameterStore.get("lockStatus").getValue().equals("True"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_LOCKED);

                    if (dynamicParameterStore.get("driveMode").getValue().equals("DRIVE"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_MODE_DRIVE);

                    if (dynamicParameterStore.get("driveMode").getValue().equals("REVERSE"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_MODE_REVERSE);
                }
                break;
            }
            case "doorStatus_rear_right": {
                if (value.equals("Opened")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_RUNNING);

                    if (dynamicParameterStore.get("lockStatus").getValue().equals("True"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_LOCKED);

                    if (dynamicParameterStore.get("driveMode").getValue().equals("DRIVE"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_MODE_DRIVE);

                    if (dynamicParameterStore.get("driveMode").getValue().equals("REVERSE"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_MODE_REVERSE);
                }
                break;
            }
            case "doorStatus_trunk": {
                if (value.equals("Opened")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_RUNNING);

                    if (!dynamicParameterStore.get("driveMode").getValue().equals("PARK"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_NOT_PARK);
                }
                break;
            }
            case "lockStatus": {
                if (value.equals("Locked")) {
                    if (dynamicParameterStore.get("doorStatus_front_left").getValue().equals("Opened")
                            || dynamicParameterStore.get("doorStatus_front_right").getValue().equals("Opened")
                            || dynamicParameterStore.get("doorStatus_rear_left").getValue().equals("Opened")
                            || dynamicParameterStore.get("doorStatus_rear_right").getValue().equals("Opened"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_ANY_DOOR_OPENED);
                }

                if (value.equals("Unlocked")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_RUNNING);
                }
                break;
            }
            case "acStatus": {
                if (value.equals("On")) {
                    if ((Double) dynamicParameterStore.get("batteryLevel").getValue() < 5)
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_CAR_OFF);
                }
                break;
                // If acStatus = On -> check 16 < cabinTemperature < 30 and door = true too long
            }
            case "cabinTemperature": {
                if (dynamicParameterStore.get("acStatus").getValue().equals("Off"))
                    throw new VsException(ErrorMessage.ManualData.ERR_IS_AIR_CONDITIONAL_OFF);

                if ((Double) value < 16)
                    throw new VsException(ErrorMessage.ManualData.ERR_IS_TEMPERATURE_TOO_LOW);

                if ((Double) value > 30)
                    throw new VsException(ErrorMessage.ManualData.ERR_IS_TEMPERATURE_TOO_HIGH);
                break;
            }
            case "seatHeating": {
                if (value.equals("On")){
                    if (!dynamicParameterStore.get("driveMode").getValue().equals("PARK"))
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_PARK);

                    if ((Double) dynamicParameterStore.get("batteryLevel").getValue() < 10)
                        throw new VsException(ErrorMessage.ManualData.ERR_IS_BATTERY_LOW);
                }
                break;
                // Tự động tắt nếu batteryLevel < 5% hoặc speed = 0 quá lâu
            }
            case "headlightStatus": {
                break;
            }
            case "checkEngine": {
                break;
            }
            default:
                throw new VsException(ErrorMessage.ManualData.ERR_IS_REALTIME_DATA);
        }
    }

    private DynamicParameterEnterRequestDto convertToEnterRequest (ManualDataUpdateRequestDto request){
        DynamicParameterEnterRequestDto dtoRequest = new DynamicParameterEnterRequestDto();
        Class<?> dtoClass = DynamicParameterEnterRequestDto.class;

        for (Map.Entry<String, DynamicParameter> entry : dynamicParameterStore.getAll().entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue().getValue();

            try {
                Field field = dtoClass.getDeclaredField(key);
                field.setAccessible(true);
                field.set(dtoRequest, value);
            } catch (NoSuchFieldException e) {
                throw new VsException(ErrorMessage.ManualData.ERR_FIELD_NOT_EXIST);
            } catch (IllegalAccessException e) {
                throw new VsException(ErrorMessage.ManualData.ERR_FIELD_CAN_NOT_UPDATE);
            }
        }

        for (Map<String, Object> item : request.getData()) {
            item.forEach((key, value) -> {
                try {
                    Field field = dtoClass.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(dtoRequest, value);
                } catch (NoSuchFieldException e) {
                    throw new VsException(ErrorMessage.ManualData.ERR_FIELD_NOT_EXIST);
                } catch (IllegalAccessException e) {
                    throw new VsException(ErrorMessage.ManualData.ERR_FIELD_CAN_NOT_UPDATE);
                }
            });
        }

        return dtoRequest;
    }
}
