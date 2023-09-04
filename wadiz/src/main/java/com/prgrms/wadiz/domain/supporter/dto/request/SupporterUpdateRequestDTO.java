package com.prgrms.wadiz.domain.supporter.dto.request;

import lombok.Builder;

@Builder
public record SupporterUpdateRequestDTO(
        String name,
        String email
) {
}
