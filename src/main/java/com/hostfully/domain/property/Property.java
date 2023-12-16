package com.hostfully.domain.property;

import java.time.LocalDateTime;
import java.util.UUID;


public record Property(UUID id, String alias, LocalDateTime createdAt) {

}
