package kpring.user.dto.request;

import lombok.Builder;

@Builder
public record CreateUserRequest(
        String email
) {
}
