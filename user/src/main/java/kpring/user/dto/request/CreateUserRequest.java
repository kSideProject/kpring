package kpring.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record CreateUserRequest(
    @NotBlank(message = "이메일이 누락되었습니다.")
    @Email(message = "invalid email")
    String email,

    @NotBlank(message = "비밀번호이(가) 누락되었습니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9!@#$]{8,15}$",
        message = "비밀번호는 최소 8자에서 15자 사이, 대문자와 소문자, 숫자가 포함되어야 하며, "
            + "특수문자 (!, @, #, $)도 사용할 수 있습니다.")
    String password,

    @NotBlank(message = "유저 이름이 누락되었습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{1,32}$",
        message = "닉네임은 영문 대소문자, 숫자, 한글로 구성되어야 하며, 1자 이상 32자 이하여야 합니다.")
    String username
) {

}
