package kpring.user.service

import kpring.core.user.dto.request.LoginRequest
import kpring.core.user.dto.request.LogoutRequest
import kpring.core.user.dto.response.LoginResponse

interface LoginService {
  fun login(request: LoginRequest): LoginResponse

  fun logout(request: LogoutRequest)
}
