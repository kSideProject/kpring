package kpring.user.service;

import kpring.user.dto.request.LoginRequest;
import kpring.user.dto.request.LogoutRequest;
import kpring.user.dto.result.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    /**
     * @throws IllegalArgumentException if request is invalid
     * @throws RuntimeException         if an unexpected error occurs
     */
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    /**
     * @throws IllegalArgumentException if request is invalid
     * @throws RuntimeException         if an unexpected error occurs
     */
    public void logout(LogoutRequest request) {
    }
}
