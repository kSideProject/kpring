package kpring.user.dto.response;

import lombok.Builder;

@Builder
public record FailMessageResponse(
        String message
) {

    public static final FailMessageResponse serverError = FailMessageResponse.builder().message("서버 오류").build();
}
