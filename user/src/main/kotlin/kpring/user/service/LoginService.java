package kpring.user.service;

import kpring.user.dto.request.LoginRequest;
import kpring.user.dto.result.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    public LoginResponse login(LoginRequest request) {
        return null;
    }

    public void logout(String token) {
    }
}
