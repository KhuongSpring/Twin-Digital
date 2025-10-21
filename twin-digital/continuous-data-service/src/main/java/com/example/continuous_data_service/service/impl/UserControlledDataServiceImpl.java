package com.example.continuous_data_service.service.impl;

import com.example.continuous_data_service.config.DynamicParameterConfig;
import com.example.continuous_data_service.config.DynamicSpecServiceConfig;
import com.example.continuous_data_service.constant.ErrorMessage;
import com.example.continuous_data_service.domain.dto.request.DynamicParameterEnterRequestDto;
import com.example.continuous_data_service.domain.dto.request.UserControlledDataUpdateRequestDto;
import com.example.continuous_data_service.domain.entity.DynamicParameter;
import com.example.continuous_data_service.exception.VsException;
import com.example.continuous_data_service.service.UserControlledDataService;
import com.example.continuous_data_service.store.DynamicParameterStore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.Field;
import java.util.Map;

@Service
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserControlledDataServiceImpl implements UserControlledDataService {

    WebClient.Builder webClientBuilder;

    DynamicParameterStore dynamicParameterStore;

    DynamicSpecServiceConfig dynamicSpecServiceConfig;

    @Override
    public void updateUserControlledData(UserControlledDataUpdateRequestDto request) {
        for (Map<String, Object> item : request.getData()) {
            item.forEach((key, value) -> {
                if (value == null)
                    throw new VsException(ErrorMessage.UserControlledData.ERR_VALUE_NULL);

                if (DynamicParameterConfig.isSystemControlled(key))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_SYSTEM_CONTROLLED_DATA);

                handleLogic(key, value);
            });
        }

//        updateManualDataToDynamicSpecService(request);
    }

    private void updateManualDataToDynamicSpecService(UserControlledDataUpdateRequestDto request) {
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
        if (dynamicParameterStore.get(paramName).getValue().equals(value))
            throw new VsException(ErrorMessage.UserControlledData.ERR_VALUE_DUPLICATE);

        switch (paramName) {
            case "gear": {
                if ((Double) dynamicParameterStore.get("speed").getValue() > 5.0)
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_SPEED_FAST);

                if (dynamicParameterStore.get("brakeFault").getValue().equals("TRUE"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_BRAKE_FAULT);

                if (dynamicParameterStore.get("doorStatus_front_left").getValue().equals("OPEN")
                        || dynamicParameterStore.get("doorStatus_front_right").getValue().equals("OPEN")
                        || dynamicParameterStore.get("doorStatus_rear_left").getValue().equals("OPEN")
                        || dynamicParameterStore.get("doorStatus_rear_right").getValue().equals("OPEN"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_ANY_DOOR_OPENED);

                if (dynamicParameterStore.get("lockStatus").getValue().equals("FALSE"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_NOT_LOCKED);

                if (dynamicParameterStore.get("motorOverheat").getValue().equals("TRUE"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_MOTOR_OVERHEAT);

                if (dynamicParameterStore.get("coolingFault").getValue().equals("TRUE"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_COOLING_FAULT);


                if (value.equals("D") && dynamicParameterStore.get(paramName).getValue().equals("R")) {
                    if ((Double) dynamicParameterStore.get("speed").getValue() != 0)
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_RUNNING);
                }

                if (value.equals("R") && dynamicParameterStore.get(paramName).getValue().equals("D")) {
                    if ((Double) dynamicParameterStore.get("speed").getValue() != 0)
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_RUNNING);
                }

                if ((Double) dynamicParameterStore.get("batteryLevel").getValue() < 5.0) {
                    if (value.equals("R") || value.equals("D"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_BATTERY_LOW);
                }

                if (value.equals("D") && dynamicParameterStore.get("chargingStatus").getValue().equals("TRUE"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CHARGING);

                if (value.equals("D") || value.equals("R")) {
                    if (dynamicParameterStore.get(paramName).getValue().equals("P")
                            && (Double) dynamicParameterStore.get("batteryLevel").getValue() < 5.0)
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_BATTERY_LOW);
                }

                break;
            }
            case "driveMode": {
                if (dynamicParameterStore.get("motorOverheat").getValue().equals("TRUE"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_MOTOR_OVERHEAT);

                if (dynamicParameterStore.get("checkEngine").getValue().equals("ON"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CHECK_ENGINE_ON);

                if (dynamicParameterStore.get("brakeFault").getValue().equals("TRUE")) {
                    if (!value.equals("Eco"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_DRIVER_MODE_NOT_ALLOW);
                }

                break;
            }
            case "oilPressure": {
                if ((Double) dynamicParameterStore.get("motorPower").getValue() > 0
                        || dynamicParameterStore.get("checkEngine").getValue().equals("ON"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_OIL_PRESSURE_NOT_ALLOW);

                if (dynamicParameterStore.get("motorOverheat").getValue().equals("TRUE"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_MOTOR_OVERHEAT);

                if (dynamicParameterStore.get("coolingFault").getValue().equals("TRUE"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_COOLING_FAULT);


                if ((Double) value < 0.5 || (Double) value > 5.0)
                    throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_OIL_PRESSURE_NOT_ALLOW);

                break;
            }
            case "airbagStatus": {
                if (dynamicParameterStore.get("checkEngine").getValue().equals("ON"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CHECK_ENGINE_ON);

                if (dynamicParameterStore.get("networkStatus").getValue().equals("FALSE"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_NETWORK_STATUS_FALSE);

                if ((Double) dynamicParameterStore.get("motorPower").getValue() > 0)
                    throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_AIRBAG_STATUS_NOT_ALLOW);

                break;
            }
            case "otaStatus": {
                if (value.equals("ON")) {
                    if (dynamicParameterStore.get("networkStatus").getValue().equals("FALSE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_NETWORK_STATUS_FALSE);

                    if ((Double) dynamicParameterStore.get("batteryLevel").getValue() < 20.0)
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_BATTERY_LOW);

                    if ((Double) dynamicParameterStore.get("motorPower").getValue() > 0)
                        throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_OTA_STATUS_NOT_ALLOW);

                    if (dynamicParameterStore.get("checkEngine").getValue().equals("ON"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CHECK_ENGINE_ON);
                }

                break;

            }
            case "doorStatus_front_left": {
                if (value.equals("OPEN")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_RUNNING);

                    if (!dynamicParameterStore.get("gear").getValue().equals("P"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_GEAR_NOT_ALLOW);

                    if (dynamicParameterStore.get("lockStatus").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_LOCKED);

                    if (dynamicParameterStore.get("airbagStatus").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_AIRBAG_STATUS);
                }
                break;
            }
            case "doorStatus_front_right": {
                if (value.equals("OPEN")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_RUNNING);

                    if (!dynamicParameterStore.get("gear").getValue().equals("P"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_GEAR_NOT_ALLOW);

                    if (dynamicParameterStore.get("lockStatus").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_LOCKED);

                    if (dynamicParameterStore.get("airbagStatus").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_AIRBAG_STATUS);
                }
                break;
            }
            case "doorStatus_rear_left": {
                if (value.equals("OPEN")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_RUNNING);

                    if (!dynamicParameterStore.get("gear").getValue().equals("P"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_GEAR_NOT_ALLOW);

                    if (dynamicParameterStore.get("lockStatus").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_LOCKED);

                    if (dynamicParameterStore.get("airbagStatus").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_AIRBAG_STATUS);
                }
                break;
            }
            case "doorStatus_rear_right": {
                if (value.equals("OPEN")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_RUNNING);

                    if (!dynamicParameterStore.get("gear").getValue().equals("P"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_GEAR_NOT_ALLOW);

                    if (dynamicParameterStore.get("lockStatus").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_LOCKED);

                    if (dynamicParameterStore.get("airbagStatus").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_AIRBAG_STATUS);
                }
                break;
            }
            case "doorStatus_trunk": {
                if (value.equals("OPEN")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_RUNNING);

                    if (!dynamicParameterStore.get("gear").getValue().equals("P"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_GEAR_NOT_ALLOW);

                    if (dynamicParameterStore.get("lockStatus").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_LOCKED);

                    if (dynamicParameterStore.get("airbagStatus").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_AIRBAG_STATUS);
                }
                break;
            }
            case "lockStatus": {
                if (value.equals("TRUE")) {
                    if (dynamicParameterStore.get("doorStatus_front_left").getValue().equals("OPEN")
                            || dynamicParameterStore.get("doorStatus_front_right").getValue().equals("OPEN")
                            || dynamicParameterStore.get("doorStatus_rear_left").getValue().equals("OPEN")
                            || dynamicParameterStore.get("doorStatus_rear_right").getValue().equals("OPEN"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_ANY_DOOR_OPENED);

                    if (dynamicParameterStore.get("gear").getValue().equals("P"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_LOCK_STATUS_NOT_ALLOW);
                }

                if (value.equals("FALSE")) {
                    if (!dynamicParameterStore.get("speed").getValue().equals(0))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CAR_RUNNING);
                }
                break;
            }
            case "acStatus": {
                if (value.equals("ON")) {
                    if ((Double) dynamicParameterStore.get("batteryLevel").getValue() < 5)
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_BATTERY_LOW);

                    if (dynamicParameterStore.get("motorOverheat").getValue().equals("TRUE"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_MOTOR_OVERHEAT);

                    if (dynamicParameterStore.get("checkEngine").getValue().equals("ON"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CHECK_ENGINE_ON);
                }
                break;
                // If acStatus = On -> check 16 < cabinTemperature < 30 and door = true too long
            }
            case "cabinTemperature": {
                if (dynamicParameterStore.get("acStatus").getValue().equals("OFF"))
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_AIR_CONDITIONAL_OFF);

                if ((Double) dynamicParameterStore.get("batteryLevel").getValue() < 15)
                    throw new VsException(ErrorMessage.UserControlledData.ERR_IS_BATTERY_LOW);

                if ((Double) dynamicParameterStore.get(paramName).getValue() < (Double) value) {
                    if (dynamicParameterStore.get("seatHeating").getValue().equals("ON")
                            && (Double) dynamicParameterStore.get(paramName).getValue() > 28.0)
                        throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_CABIN_TEMPERATURE_NOT_ALLOW);
                }

                if (Math.abs((Double) dynamicParameterStore.get(paramName).getValue() - (Double) value) > 2)
                    throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_CABIN_TEMPERATURE_NOT_ALLOW);

                if ((Double) value < 16)
                    throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_CABIN_TEMPERATURE_NOT_ALLOW);

                if ((Double) value > 30)
                    throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_CABIN_TEMPERATURE_NOT_ALLOW);
                break;
            }
            case "seatHeating": {
                if (value.equals("On")) {
                    if ((Double) dynamicParameterStore.get("batteryLevel").getValue() < 20)
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_BATTERY_LOW);

                    if ((Double) dynamicParameterStore.get("cabinTemperature").getValue() > 28)
                        throw new VsException(ErrorMessage.UserControlledData.ERR_CHANGE_SEAT_HEATING_NOT_ALLOW);

                    if (dynamicParameterStore.get("checkEngine").getValue().equals("ON"))
                        throw new VsException(ErrorMessage.UserControlledData.ERR_IS_CHECK_ENGINE_ON);
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
                throw new VsException(ErrorMessage.UserControlledData.ERR_IS_SYSTEM_CONTROLLED_DATA);
        }
    }

    private DynamicParameterEnterRequestDto convertToEnterRequest(UserControlledDataUpdateRequestDto request) {
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
                throw new VsException(ErrorMessage.UserControlledData.ERR_FIELD_NOT_EXIST);
            } catch (IllegalAccessException e) {
                throw new VsException(ErrorMessage.UserControlledData.ERR_FIELD_CAN_NOT_UPDATE);
            }
        }

        for (Map<String, Object> item : request.getData()) {
            item.forEach((key, value) -> {
                try {
                    Field field = dtoClass.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(dtoRequest, value);
                } catch (NoSuchFieldException e) {
                    throw new VsException(ErrorMessage.UserControlledData.ERR_FIELD_NOT_EXIST);
                } catch (IllegalAccessException e) {
                    throw new VsException(ErrorMessage.UserControlledData.ERR_FIELD_CAN_NOT_UPDATE);
                }
            });
        }

        return dtoRequest;
    }
}
