package kpring.user.dto.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UpdateUserProfileRequest(
    String email,
    String username,
    String password,
    String newPassword
) {

}