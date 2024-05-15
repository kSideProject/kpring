package kpring.user.service

import kpring.user.dto.request.LoginRequest
import kpring.user.dto.request.LogoutRequest
import kpring.user.dto.response.LoginResponse

interface LoginService {
  fun login(request: LoginRequest): LoginResponse

  fun logout(request: LogoutRequest)
}
