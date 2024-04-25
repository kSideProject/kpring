package kpring.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CreateUserRequest(
        @NotBlank(message = "필수 입력값이 누락되었습니다.")
        @Email(message = "invalid email")
        String email
) {
}
