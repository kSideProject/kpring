package kpring.user.dto.response;

import lombok.Builder;

@Builder
public record UpdateUserProfileResponse(
    String email,
    String username
) {
}