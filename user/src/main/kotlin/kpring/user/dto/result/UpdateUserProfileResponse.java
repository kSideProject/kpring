package kpring.user.dto.result;

import lombok.Builder;

@Builder
public record UpdateUserProfileResponse(
        String email
) {
}
