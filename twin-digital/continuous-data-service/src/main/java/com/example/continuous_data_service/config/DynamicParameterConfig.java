package com.example.continuous_data_service.config;

import com.example.continuous_data_service.domain.entity.ParameterType;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class DynamicParameterConfig {

    public static final Map<String, ParameterType> PARAMETER_TYPE_MAP = Map.ofEntries(
            // ====== REALTIME PARAMETERS ======
            Map.entry("speed", ParameterType.REALTIME),
            Map.entry("rpm", ParameterType.REALTIME),
            Map.entry("gear", ParameterType.REALTIME),
            Map.entry("odometer", ParameterType.REALTIME),
            Map.entry("tripDistance", ParameterType.REALTIME),
            Map.entry("batteryLevel", ParameterType.REALTIME),
            Map.entry("chargingStatus", ParameterType.REALTIME),
            Map.entry("rangeRemaining", ParameterType.REALTIME),
            Map.entry("batteryTemperature", ParameterType.REALTIME),
            Map.entry("chargingPower", ParameterType.REALTIME),
            Map.entry("chargingTimeRemaining", ParameterType.REALTIME),
            Map.entry("energyConsumption", ParameterType.REALTIME),
            Map.entry("motorPower", ParameterType.REALTIME),
            Map.entry("motorTemperature", ParameterType.REALTIME),
            Map.entry("torqueOutput", ParameterType.REALTIME),
            Map.entry("coolantTemperature", ParameterType.REALTIME),
            Map.entry("brakeStatus", ParameterType.REALTIME),
            Map.entry("absActive", ParameterType.REALTIME),
            Map.entry("brakePadWear", ParameterType.REALTIME),
            Map.entry("brakeTemperature", ParameterType.REALTIME),
            Map.entry("tirePressure", ParameterType.REALTIME),
            Map.entry("tireTemperature", ParameterType.REALTIME),
            Map.entry("tpmsWarning", ParameterType.REALTIME),
            Map.entry("gps_latitude", ParameterType.REALTIME),
            Map.entry("gps_longitude", ParameterType.REALTIME),
            Map.entry("gps_speed", ParameterType.REALTIME),
            Map.entry("gps_altitude", ParameterType.REALTIME),
            Map.entry("gps_heading", ParameterType.REALTIME),
            Map.entry("signalStrength", ParameterType.REALTIME),
            Map.entry("networkStatus", ParameterType.REALTIME),
            Map.entry("coolingFault", ParameterType.REALTIME),
            Map.entry("motorOverheat", ParameterType.REALTIME),
            Map.entry("tireLeak", ParameterType.REALTIME),
            Map.entry("batteryFault", ParameterType.REALTIME),
            Map.entry("brakeFault", ParameterType.REALTIME),

            // ====== MANUAL PARAMETERS ======
            Map.entry("driveMode", ParameterType.MANUAL),
            Map.entry("oilPressure", ParameterType.MANUAL),
            Map.entry("airbagStatus", ParameterType.MANUAL),
            Map.entry("otaStatus", ParameterType.MANUAL),
            Map.entry("doorStatus_front_left", ParameterType.MANUAL),
            Map.entry("doorStatus_front_right", ParameterType.MANUAL),
            Map.entry("doorStatus_rear_left", ParameterType.MANUAL),
            Map.entry("doorStatus_rear_right", ParameterType.MANUAL),
            Map.entry("doorStatus_trunk", ParameterType.MANUAL),
            Map.entry("lockStatus", ParameterType.MANUAL),
            Map.entry("acStatus", ParameterType.MANUAL),
            Map.entry("cabinTemperature", ParameterType.MANUAL),
            Map.entry("seatHeating", ParameterType.MANUAL),
            Map.entry("headlightStatus", ParameterType.MANUAL),
            Map.entry("checkEngine", ParameterType.MANUAL)
    );

    public static ParameterType getType(String parameterName) {
        return PARAMETER_TYPE_MAP.getOrDefault(parameterName, ParameterType.REALTIME);
    }

    public static boolean isRealtime(String parameterName) {
        return getType(parameterName) == ParameterType.REALTIME;
    }

    public static boolean isManual(String parameterName) {
        return getType(parameterName) == ParameterType.MANUAL;
    }
}

