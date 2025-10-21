package com.example.continuous_data_service.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Thông số động của xe")
public class DynamicParameterEnterRequestDto {

    @Schema(description = "Tốc độ xe (km/h)", example = "null")
    Double speed;

    @Schema(description = "Vòng tua máy (RPM)", example = "null")
    Double rpm;

    @Schema(description = "Số đang gài (P, R, N, D)", example = "null")
    String gear;

    @Schema(description = "Số km đã đi (km)", example = "null")
    Double odometer;

    @Schema(description = "Quãng đường chuyến đi (km)", example = "null")
    Double tripDistance;

    @Schema(description = "Chế độ lái (Eco, Comfort, Sport)", example = "null")
    String driveMode;

    @Schema(description = "Mức pin còn lại (%)", example = "null")
    Double batteryLevel;

    @Schema(description = "Trạng thái sạc (TRUE/FALSE)", example = "null")
    String chargingStatus;

    @Schema(description = "Quãng đường còn lại ước tính (km)", example = "null")
    Double rangeRemaining;

    @Schema(description = "Nhiệt độ pin (°C)", example = "null")
    Double batteryTemperature;

    @Schema(description = "Công suất sạc hiện tại (kW)", example = "null")
    Double chargingPower;

    @Schema(description = "Thời gian sạc còn lại (phút)", example = "null")
    Double chargingTimeRemaining;

    @Schema(description = "Mức tiêu thụ năng lượng (kWh/100km)", example = "null")
    Double energyConsumption;

    @Schema(description = "Công suất động cơ (kW)", example = "null")
    Double motorPower;

    @Schema(description = "Nhiệt độ động cơ (°C)", example = "null")
    Double motorTemperature;

    @Schema(description = "Mô-men xoắn (Nm)", example = "null")
    Double torqueOutput;

    @Schema(description = "Nhiệt độ dung dịch làm mát (°C)", example = "null")
    Double coolantTemperature;

    @Schema(description = "Áp suất dầu (bar)", example = "null")
    Double oilPressure;

    @Schema(description = "Trạng thái phanh (TRUE/FALSE)", example = "null")
    String brakeStatus;

    @Schema(description = "ABS đang hoạt động (TRUE/FALSE)", example = "null")
    String absActive;

    @Schema(description = "Mức mòn má phanh (%)", example = "null")
    Double brakePadWear;

    @Schema(description = "Nhiệt độ phanh (°C)", example = "null")
    Double brakeTemperature;

    @Schema(description = "Trạng thái túi khí (TRUE/FALSE)", example = "null")
    String airbagStatus;

    @Schema(description = "Áp suất lốp (bar)", example = "null")
    Double tirePressure;

    @Schema(description = "Nhiệt độ lốp (°C)", example = "null")
    Double tireTemperature;

    @Schema(description = "Cảnh báo TPMS (ON/OFF)", example = "null")
    String tpmsWarning;

    @Schema(description = "Vĩ độ GPS (°)", example = "null")
    Double gps_latitude;

    @Schema(description = "Kinh độ GPS (°)", example = "null")
    Double gps_longitude;

    @Schema(description = "Tốc độ GPS (km/h)", example = "null")
    Double gps_speed;

    @Schema(description = "Cường độ tín hiệu GPS (%)", example = "null")
    Double signalStrength;

    @Schema(description = "Độ cao GPS (m)", example = "null")
    Double gps_altitude;

    @Schema(description = "Hướng di chuyển GPS (°)", example = "null")
    Double gps_heading;

    @Schema(description = "Trạng thái mạng (TRUE/FALSE)", example = "null")
    String networkStatus;

    @Schema(description = "Trạng thái OTA (cập nhật phần mềm) (ON/OFF)", example = "null")
    String otaStatus;

    @Schema(description = "Cửa trước bên trái (OPEN/CLOSE)", example = "null")
    String doorStatus_front_left;

    @Schema(description = "Cửa trước bên phải (OPEN/CLOSE)", example = "null")
    String doorStatus_front_right;

    @Schema(description = "Cửa sau bên trái (OPEN/CLOSE)", example = "null")
    String doorStatus_rear_left;

    @Schema(description = "Cửa sau bên phải (OPEN/CLOSE)", example = "null")
    String doorStatus_rear_right;

    @Schema(description = "Cốp sau (OPEN/CLOSE)", example = "null")
    String doorStatus_trunk;

    @Schema(description = "Trạng thái khóa cửa (TRUE/FALSE)", example = "null")
    String lockStatus;

    @Schema(description = "Trạng thái điều hòa (ON/OFF)", example = "null")
    String acStatus;

    @Schema(description = "Nhiệt độ trong cabin (°C)", example = "null")
    Double cabinTemperature;

    @Schema(description = "Sưởi ghế (ON/OFF)", example = "null")
    String seatHeating;

    @Schema(description = "Trạng thái đèn pha (ON/OFF)", example = "null")
    String headlightStatus;

    @Schema(description = "Đèn báo lỗi động cơ (ON/OFF)", example = "null")
    String checkEngine;

    @Schema(description = "Lỗi pin (TRUE/FALSE)", example = "null")
    String batteryFault;

    @Schema(description = "Lỗi phanh (TRUE/FALSE)", example = "null")
    String brakeFault;

    @Schema(description = "Lỗi hệ thống làm mát (TRUE/FALSE)", example = "null")
    String coolingFault;

    @Schema(description = "Động cơ quá nhiệt (TRUE/FALSE)", example = "null")
    String motorOverheat;

    @Schema(description = "Phát hiện rò rỉ lốp (TRUE/FALSE)", example = "null")
    String tireLeak;
}