package kpring.user.dto.request;

import lombok.Builder;

@Builder
public record LogoutRequest(
        String accessToken,
        String refreshToken
) {
}
