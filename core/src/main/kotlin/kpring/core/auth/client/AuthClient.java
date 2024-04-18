package kpring.core.auth.client;

import kpring.core.auth.dto.request.CreateTokenRequest;
import kpring.core.auth.dto.request.TokenValidationRequest;
import kpring.core.auth.dto.response.CreateTokenResponse;
import kpring.core.auth.dto.response.ReCreateAccessTokenResponse;
import kpring.core.auth.dto.response.TokenValidationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface AuthClient {

    @PostExchange("/api/v1/token")
    ResponseEntity<CreateTokenResponse> createToken(
            @RequestBody CreateTokenRequest request
    );

    @DeleteExchange("/api/v1/token/{tokenData}")
    ResponseEntity<Void> deleteToken(
            @PathVariable("tokenData") String tokenData
    );

    @PostExchange("/api/v1/token/access_token")
    ResponseEntity<ReCreateAccessTokenResponse> recreateAccessToken(
            @RequestHeader CreateTokenRequest request
    );

    @PostExchange("/api/v1/validation")
    ResponseEntity<TokenValidationResponse> validateToken(
            @RequestHeader("Authorization") String token,
            @RequestBody TokenValidationRequest request
    );
}
