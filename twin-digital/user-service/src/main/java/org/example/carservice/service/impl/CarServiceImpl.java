package org.example.carservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.carservice.config.DynamicSpecServiceConfig;
import org.example.carservice.config.StaticSpecServiceConfig;
import org.example.carservice.constant.ErrorMessage;
import org.example.carservice.constant.SuccessMessage;
import org.example.carservice.domain.dto.request.EnterCarRequestDto;
import org.example.carservice.domain.dto.response.DynamicSpecGroupResponseDto;
import org.example.carservice.domain.dto.response.EnterCarResponseDto;
import org.example.carservice.domain.dto.response.StaticSpecGroupResponseDto;
import org.example.carservice.domain.entity.User;
import org.example.carservice.exception.ServiceUnavailableException;
import org.example.carservice.exception.VsException;
import org.example.carservice.repository.UserRepository;
import org.example.carservice.service.CarService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarServiceImpl implements CarService {

    WebClient.Builder webClientBuilder;

    StaticSpecServiceConfig staticSpecServiceConfig;

    DynamicSpecServiceConfig dynamicSpecServiceConfig;

    UserRepository userRepository;

    @Override
    public EnterCarResponseDto enterCar(EnterCarRequestDto request) {

        User user = userRepository.findById(request.getUserId())
              .orElseThrow(() -> new VsException(ErrorMessage.User.ERR_USER_NOT_FOUND));

        List<StaticSpecGroupResponseDto> staticSpecGroups;
        List<DynamicSpecGroupResponseDto> dynamicSpecGroups;

            try {
                String carModelName = request.getCarModelName();
                
                staticSpecGroups = importCarSpecification(carModelName);

                dynamicSpecGroups = initDynamicSpec();

                user.setCarModelName(carModelName);
                userRepository.save(user);
                log.info("Car model name saved to database: {}", carModelName);

                return EnterCarResponseDto.builder()
                        .carModelName(carModelName)
                        .userId(user.getId())
                        .staticSpecGroups(staticSpecGroups)
                        .dynamicSpecGroups(dynamicSpecGroups)
                        .message(SuccessMessage.Car.ENTER_CAR_SUCCESS)
                        .build();

            } catch (Exception e) {
                log.error("Error during enterCar process", e);
                throw new VsException(ErrorMessage.Car.ERR_ENTER_CAR_FAILED + ": " + e.getMessage());
            }
    }

    @Override
    public EnterCarResponseDto getCarByUserId(String userId) {
        User user = userRepository.findById(userId)
              .orElseThrow(() -> new VsException(ErrorMessage.User.ERR_USER_NOT_FOUND));

        if (user.getCarModelName() == null || user.getCarModelName().isEmpty()) {
            throw new VsException(ErrorMessage.Car.ERR_USER_HAS_NO_CAR);
        }

        try {
            List<StaticSpecGroupResponseDto> staticSpecGroups = getStaticSpec();

            List<DynamicSpecGroupResponseDto> dynamicSpecGroups = getDynamicSpec();

            return EnterCarResponseDto.builder()
                    .carModelName(user.getCarModelName())
                    .userId(user.getId())
                    .staticSpecGroups(staticSpecGroups)
                    .dynamicSpecGroups(dynamicSpecGroups)
                    .message("Get car information successfully")
                    .build();

        } catch (Exception e) {
            throw new VsException(ErrorMessage.Car.ERR_GET_CAR_FAILED + ": " + e.getMessage());
        }
    }

    private List<StaticSpecGroupResponseDto> getStaticSpec() {
        try {
            WebClient webClient = webClientBuilder
                    .baseUrl(staticSpecServiceConfig.getUrl())
                    .build();

            Map<String, Object> response = webClient.get()
                    .uri("/static-spec")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();

            if (response != null && response.containsKey("data")) {
                Object data = response.get("data");
                if (data instanceof List<?> rawList) {
                    return rawList.stream()
                          .map(this::convertToStaticSpecGroup)
                          .toList();
                }
            }

            throw new VsException("Invalid response from Static Spec Service");

        } catch (Exception e) {
            log.error("Error calling Static Spec Service", e);
            throw new ServiceUnavailableException(ErrorMessage.Car.ERR_STATIC_SPEC_SERVICE_UNAVAILABLE);
        }
    }

    private List<DynamicSpecGroupResponseDto> getDynamicSpec() {
        try {
            WebClient webClient = webClientBuilder
                    .baseUrl(dynamicSpecServiceConfig.getUrl())
                    .build();

            Map<String, Object> response = webClient.get()
                    .uri("/dynamic-spec")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();

            if (response != null && response.containsKey("data")) {
                Object data = response.get("data");
                if (data instanceof List<?>) {
                    List<?> rawList = (List<?>) data;
                    return rawList.stream()
                            .map(this::convertToDynamicSpecGroup)
                            .toList();
                }
            }

            throw new VsException("Invalid response from Dynamic Spec Service");

        } catch (Exception e) {
            log.error("Error calling Dynamic Spec Service", e);
            throw new ServiceUnavailableException(ErrorMessage.Car.ERR_DYNAMIC_SPEC_SERVICE_UNAVAILABLE);
        }
    }

    private List<StaticSpecGroupResponseDto> importCarSpecification(String carModelName) {
        try {
            WebClient webClient = webClientBuilder
                    .baseUrl(staticSpecServiceConfig.getUrl())
                    .build();

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("carModelName", carModelName);

            webClient.post()
                    .uri("/static-spec/import")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            Map<String, Object> response = webClient.get()
                    .uri("/static-spec")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();

            if (response != null && response.containsKey("data")) {
                Object data = response.get("data");
                if (data instanceof List<?> rawList) {
                    return rawList.stream()
                          .map(this::convertToStaticSpecGroup)
                          .toList();
                }
            }

            throw new VsException("Invalid response from Static Spec Service");

        } catch (Exception e) {
            log.error("Error calling Static Spec Service", e);
            throw new ServiceUnavailableException(ErrorMessage.Car.ERR_STATIC_SPEC_SERVICE_UNAVAILABLE);
        }
    }

    private List<DynamicSpecGroupResponseDto> initDynamicSpec() {
        try {
            WebClient webClient = webClientBuilder
                    .baseUrl(dynamicSpecServiceConfig.getUrl())
                    .build();

            Map<String, Object> response = webClient.post()
                    .uri("/dynamic-spec/init-spec")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                    })
                    .block();

            if (response != null && response.containsKey("data")) {
                Object data = response.get("data");
                if (data instanceof List<?>) {
                    List<?> rawList = (List<?>) data;
                    return rawList.stream()
                            .map(this::convertToDynamicSpecGroup)
                            .toList();
                }
            }

            throw new VsException("Invalid response from Dynamic Spec Service");

        } catch (Exception e) {
            log.error("Error calling Dynamic Spec Service", e);
            throw new ServiceUnavailableException(ErrorMessage.Car.ERR_DYNAMIC_SPEC_SERVICE_UNAVAILABLE);
        }
    }

    private StaticSpecGroupResponseDto convertToStaticSpecGroup(Object item) {
        if (item instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) item;
            StaticSpecGroupResponseDto group = new StaticSpecGroupResponseDto();

            if (map.containsKey("id")) {
                Object id = map.get("id");
                group.setId(id instanceof Number ? ((Number) id).longValue() : null);
            }
            if (map.containsKey("groupName")) {
                group.setGroupName((String) map.get("groupName"));
            }
            return group;
        }
        return null;
    }

    private DynamicSpecGroupResponseDto convertToDynamicSpecGroup(Object item) {
        if (item instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) item;
            DynamicSpecGroupResponseDto group = new DynamicSpecGroupResponseDto();

            if (map.containsKey("groupName")) {
                group.setGroupName((String) map.get("groupName"));
            }
            return group;
        }
        return null;
    }
}
