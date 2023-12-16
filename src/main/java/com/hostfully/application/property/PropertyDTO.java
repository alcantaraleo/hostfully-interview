package com.hostfully.application.property;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record PropertyDTO(UUID id, @NotNull(message = "Property alias is required") String alias, LocalDateTime createdAt) {

}
