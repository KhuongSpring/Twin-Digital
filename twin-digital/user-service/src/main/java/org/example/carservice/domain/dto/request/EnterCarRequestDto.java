package org.example.carservice.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.carservice.constant.ErrorMessage;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Request để đưa xe vào hệ thống")
public class EnterCarRequestDto {

  @NotBlank(message = ErrorMessage.Car.ERR_CAR_MODEL_REQUIRED)
  @Pattern(regexp = "^(vf[3-9]|VF[3-9])$", message = ErrorMessage.Car.ERR_CAR_MODEL_INVALID_FORMAT)
  @Schema(description = "Tên model xe (VF3-VF9)", example = "VF8")
  String carModelName;

  @NotBlank(message = ErrorMessage.User.ERR_USER_ID_REQUIRED)
  @Schema(description = "ID của người dùng", example = "550e8400-e29b-41d4-a716-446655440000")
  String userId;
}
