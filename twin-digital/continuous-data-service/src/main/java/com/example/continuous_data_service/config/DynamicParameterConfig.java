package com.example.continuous_data_service.config;

import com.example.continuous_data_service.domain.entity.ParameterType;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DynamicParameterConfig {

    public static final Map<String, ParameterType> PARAMETER_TYPE_MAP = Map.ofEntries(
            // ====== REALTIME PARAMETERS ======
            Map.entry("speed", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("rpm", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("odometer", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("tripDistance", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("batteryLevel", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("chargingStatus", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("rangeRemaining", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("batteryTemperature", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("chargingPower", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("chargingTimeRemaining", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("energyConsumption", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("motorPower", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("motorTemperature", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("torqueOutput", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("coolantTemperature", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("brakeStatus", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("absActive", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("brakePadWear", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("brakeTemperature", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("tirePressure", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("tireTemperature", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("tpmsWarning", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("gps_latitude", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("gps_longitude", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("gps_speed", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("gps_altitude", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("gps_heading", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("signalStrength", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("networkStatus", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("coolingFault", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("motorOverheat", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("tireLeak", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("batteryFault", ParameterType.SYSTEM_CONTROLLED),
            Map.entry("brakeFault", ParameterType.SYSTEM_CONTROLLED),

            // ====== MANUAL PARAMETERS ======
            Map.entry("gear", ParameterType.USER_CONTROLLED),
            Map.entry("driveMode", ParameterType.USER_CONTROLLED),
            Map.entry("oilPressure", ParameterType.USER_CONTROLLED),
            Map.entry("airbagStatus", ParameterType.USER_CONTROLLED),
            Map.entry("otaStatus", ParameterType.USER_CONTROLLED),
            Map.entry("doorStatus_front_left", ParameterType.USER_CONTROLLED),
            Map.entry("doorStatus_front_right", ParameterType.USER_CONTROLLED),
            Map.entry("doorStatus_rear_left", ParameterType.USER_CONTROLLED),
            Map.entry("doorStatus_rear_right", ParameterType.USER_CONTROLLED),
            Map.entry("doorStatus_trunk", ParameterType.USER_CONTROLLED),
            Map.entry("lockStatus", ParameterType.USER_CONTROLLED),
            Map.entry("acStatus", ParameterType.USER_CONTROLLED),
            Map.entry("cabinTemperature", ParameterType.USER_CONTROLLED),
            Map.entry("seatHeating", ParameterType.USER_CONTROLLED),
            Map.entry("headlightStatus", ParameterType.USER_CONTROLLED),
            Map.entry("checkEngine", ParameterType.USER_CONTROLLED)
    );

    public static ParameterType getType(String parameterName) {
        return PARAMETER_TYPE_MAP.getOrDefault(parameterName, ParameterType.SYSTEM_CONTROLLED);
    }

    public static boolean isSystemControlled(String parameterName) {
        return getType(parameterName) == ParameterType.SYSTEM_CONTROLLED;
    }

    public static boolean isUserControlled(String parameterName) {
        return getType(parameterName) == ParameterType.USER_CONTROLLED;
    }
}

