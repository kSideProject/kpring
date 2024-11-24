package kpring.user.dto.response;

import lombok.Builder;

@Builder
public record GetUserProfileResponse(
  Long userId,
  String email,
  String username,
  String filename
) {

}
