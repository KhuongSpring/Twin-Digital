package org.example.staticspecservice.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.staticspecservice.constant.ErrorMessage;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarModelImportRequestDto {

    @NotBlank(message = ErrorMessage.StaticSpec.ERR_CAR_MODEL_REQUIRED)
    @Pattern(regexp = "^(vf[3-9]|VF[3-9])$", message = ErrorMessage.StaticSpec.ERR_CAR_MODEL_INVALID_FORMAT)
    String carModelName;
}
