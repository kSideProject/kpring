package kpring.user.dto.result;

import lombok.Builder;

@Builder
public record FailMessageResponse(
        String message
) {
}
