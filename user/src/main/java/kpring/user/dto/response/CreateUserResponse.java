package kpring.user.dto.response;

import lombok.Builder;

@Builder
public record CreateUserResponse(
    Long id,
    String email
) {

}
