package org.example.staticspecservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaticParameterImportRequestDto {

    // General
    @Schema(example = "null")
    String modelName;
    @Schema(example = "null")
    String variant;
    @Schema(example = "null")
    Double batteryCapacity;
    @Schema(example = "null")
    String driveType;
    @Schema(example = "null")
    String motorType;

    // Dimension
    @Schema(example = "null")
    Double wheelbase;
    @Schema(example = "null")
    Double length;
    @Schema(example = "null")
    Double width;
    @Schema(example = "null")
    Double height;
    @Schema(example = "null")
    Double groundClearance;
    @Schema(example = "null")
    Double turningCircle;
    @Schema(example = "null")
    Double trunkCapacitySeatUp;
    @Schema(example = "null")
    Double trunkCapacitySeatDown;
    @Schema(example = "null")
    Double frunkCapacity;

    // Weight
    @Schema(example = "null")
    Double curbWeight;
    @Schema(example = "null")
    Double payload;
    @Schema(example = "null")
    Double roofLoad;
    @Schema(example = "null")
    Double towingCapacityUnbraked;
    @Schema(example = "null")
    Double towingCapacityBraked;

    // Powertrain
    @Schema(example = "null")
    Double maxPower;
    @Schema(example = "null")
    Double maxTorque;
    @Schema(example = "null")
    Double maxSpeed;
    @Schema(example = "null")
    Double acceleration_0_100;
    @Schema(example = "null")
    Double rangeEPA;
    @Schema(example = "null")
    Double chargingTimeFast;
    @Schema(example = "null")
    String chargingStandard;
    @Schema(example = "null")
    String portableCharger;

    // Chassis
    @Schema(example = "null")
    String frontSuspension;
    @Schema(example = "null")
    String rearSuspension;
    @Schema(example = "null")
    String brakes;
    @Schema(example = "null")
    String steeringAssist;
    @Schema(example = "null")
    String wheelSize;
    @Schema(example = "null")
    String tireType;
    @Schema(example = "null")
    Boolean spareWheel;
    @Schema(example = "null")
    Boolean inflatorKit;

    // Exterior
    @Schema(example = "null")
    String headlamp;
    @Schema(example = "null")
    Boolean drl;
    @Schema(example = "null")
    Boolean fogLamp;
    @Schema(example = "null")
    Boolean corneringLight;
    @Schema(example = "null")
    Boolean welcomeLight;
    @Schema(example = "null")
    Boolean signatureLights;
    @Schema(example = "null")
    String doorHandle;
    @Schema(example = "null")
    String doorLatch;
    @Schema(example = "null")
    String windows;
    @Schema(example = "null")
    String liftgate;
    @Schema(example = "null")
    Boolean rearGlassHeated;
    @Schema(example = "null")
    String roofType;

    // Interior
    @Schema(example = "null")
    Double seatCapacity;
    @Schema(example = "null")
    String seatMaterial;
    @Schema(example = "null")
    String driverSeat;
    @Schema(example = "null")
    String passengerSeat;
    @Schema(example = "null")
    String secondRowSeat;
    @Schema(example = "null")
    String thirdRowSeat;
    @Schema(example = "null")
    Boolean captainChair;
    @Schema(example = "null")
    String steeringWheel;
    @Schema(example = "null")
    Double infotainmentScreen;
    @Schema(example = "null")
    Double rearScreen;
    @Schema(example = "null")
    Boolean hud;
    @Schema(example = "null")
    String soundSystem;
    @Schema(example = "null")
    Boolean ambientLighting;
    @Schema(example = "null")
    Boolean wirelessCharger;
    @Schema(example = "null")
    String acType;
    @Schema(example = "null")
    Boolean sunBlinds;

    // Safety
    @Schema(example = "null")
    Double airbags;
    @Schema(example = "null")
    Boolean isofix;
    @Schema(example = "null")
    Boolean seatbeltPretensioners;
    @Schema(example = "null")
    String tpms;
    @Schema(example = "null")
    Boolean immobilizer;
    @Schema(example = "null")
    Boolean theftAlarm;
    @Schema(example = "null")
    Boolean avas;

    // ADAS
    @Schema(example = "null")
    Boolean laneAssist;
    @Schema(example = "null")
    Boolean laneDepartureWarning;
    @Schema(example = "null")
    Boolean laneCenteringAssist;
    @Schema(example = "null")
    Boolean trafficJamAssist;
    @Schema(example = "null")
    Boolean highwayAssist;
    @Schema(example = "null")
    Boolean adaptiveCruiseControl;
    @Schema(example = "null")
    Boolean intelligentSpeedAdaptation;
    @Schema(example = "null")
    Boolean trafficSignRecognition;
    @Schema(example = "null")
    Boolean forwardCollisionWarning;
    @Schema(example = "null")
    Boolean rearCrossTrafficAlert;
    @Schema(example = "null")
    Boolean blindSpotDetection;
    @Schema(example = "null")
    Boolean doorOpenWarning;
    @Schema(example = "null")
    Boolean autoEmergencyBrake;
    @Schema(example = "null")
    Boolean emergencyLaneKeepAssist;
    @Schema(example = "null")
    Boolean parkingAssist;
    @Schema(example = "null")
    Boolean surroundViewCamera;
    @Schema(example = "null")
    Boolean autoHighBeam;

    // Connectivity
    @Schema(example = "null")
    Boolean vfStandardConnect;
    @Schema(example = "null")
    Boolean vfPrimeConnect;
    @Schema(example = "null")
    Boolean otaUpdate;
    @Schema(example = "null")
    Boolean appleCarPlay;
    @Schema(example = "null")
    Boolean androidAuto;
    @Schema(example = "null")
    Boolean virtualAssistant;
    @Schema(example = "null")
    Boolean smartHomeControl;
    @Schema(example = "null")
    Boolean videoStreaming;
    @Schema(example = "null")
    Boolean games;
    @Schema(example = "null")
    Boolean webBrowser;
    @Schema(example = "null")
    Boolean eSIM;
}