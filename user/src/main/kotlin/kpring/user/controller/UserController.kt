package kpring.user.controller

import kpring.core.auth.client.AuthClient
import kpring.core.auth.dto.request.TokenValidationRequest
import kpring.user.dto.request.CreateUserRequest
import kpring.user.dto.request.UpdateUserProfileRequest
import kpring.user.dto.result.CreateUserResponse
import kpring.user.dto.result.GetUserProfileResponse
import kpring.user.dto.result.UpdateUserProfileResponse
import kpring.user.exception.ErrorCode
import kpring.user.exception.ExceptionWrapper
import kpring.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserController(
    val userService: UserService,
    val authClient: AuthClient,
) {

    @GetMapping("/user/{userId}")
    fun getUserProfile(
        @PathVariable userId: Long,
    ): ResponseEntity<GetUserProfileResponse> {
        val response = userService.getProfile(userId)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/user")
    fun createUser(
        @Validated @RequestBody request: CreateUserRequest,
    ): ResponseEntity<CreateUserResponse> {
        val response = userService.createUser(request)
        return ResponseEntity.ok(response)
    }

    @PatchMapping("/user/{userId}")
    fun updateUserProfile(
        @RequestHeader("Authorization") token: String,
        @PathVariable userId: Long,
        @RequestBody request: UpdateUserProfileRequest,
    ): ResponseEntity<UpdateUserProfileResponse> {
        val validationResult = authClient.validateToken(token, TokenValidationRequest(userId = userId.toString()))
        if(!validationResult.body!!.isValid){
            throw ExceptionWrapper(ErrorCode.NOT_ALLOWED)
        }
        val response = userService.updateProfile(userId, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/user/{userId}")
    fun exitUser(
        @RequestHeader("Authorization") token: String,
        @PathVariable userId: Long,
    ): ResponseEntity<Any> {
        val isExit = userService.exitUser(userId)

        return if (isExit) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}
