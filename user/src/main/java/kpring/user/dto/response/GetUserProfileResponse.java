package kpring.user.dto.response;

import lombok.Builder;

@Builder
public record GetUserProfileResponse(
        String email
) {
}
