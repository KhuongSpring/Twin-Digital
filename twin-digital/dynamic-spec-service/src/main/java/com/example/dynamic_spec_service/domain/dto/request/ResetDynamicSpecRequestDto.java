package com.example.dynamic_spec_service.domain.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResetDynamicSpecRequestDto {
    List<Long> listId;
}
