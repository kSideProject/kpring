package kpring.user.controller

import kpring.user.dto.request.LoginRequest
import kpring.user.dto.request.LogoutRequest
import kpring.user.service.LoginService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class LoginController(val loginService: LoginService) {
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
    ): ResponseEntity<*> {
        val response = loginService.login(request)
        return ResponseEntity
            .ok()
            .header("Authorization", response.accessToken)
            .body(response)
    }

    @PostMapping("/logout")
    fun logout(
        @RequestBody request: LogoutRequest
    ): ResponseEntity<Any> {
        loginService.logout(request)
        return ResponseEntity.ok().build()
    }
}