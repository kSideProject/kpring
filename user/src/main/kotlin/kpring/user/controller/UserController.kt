package kpring.user.controller

import kpring.core.global.dto.response.ApiResponse
import kpring.user.dto.request.CreateUserRequest
import kpring.user.dto.request.UpdateUserProfileRequest
import kpring.user.dto.response.CreateUserResponse
import kpring.user.dto.response.GetUserProfileResponse
import kpring.user.dto.response.UpdateUserProfileResponse
import kpring.user.global.AuthValidator
import kpring.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class UserController(
  val userService: UserService,
  val authValidator: AuthValidator,
) {
  @GetMapping("/user/{userId}")
  fun getUserProfile(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
  ): ResponseEntity<ApiResponse<GetUserProfileResponse>> {
    authValidator.checkIfAccessTokenAndGetUserId(token)
    val response = userService.getProfile(userId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @PostMapping("/user")
  fun createUser(
    @Validated @RequestBody request: CreateUserRequest,
  ): ResponseEntity<ApiResponse<CreateUserResponse>> {
    val response = userService.createUser(request)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @PatchMapping("/user/{userId}")
  fun updateUserProfile(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
    @Validated @RequestPart(value = "json") request: UpdateUserProfileRequest,
    @RequestPart(value = "file") multipartFile: MultipartFile,
  ): ResponseEntity<ApiResponse<UpdateUserProfileResponse>> {
    val validatedUserId = authValidator.checkIfAccessTokenAndGetUserId(token)
    authValidator.checkIfUserIsSelf(userId.toString(), validatedUserId)

    val response = userService.updateProfile(userId, request, multipartFile)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @DeleteMapping("/user/{userId}")
  fun exitUser(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
  ): ResponseEntity<ApiResponse<Any>> {
    val validatedUserId = authValidator.checkIfAccessTokenAndGetUserId(token)
    authValidator.checkIfUserIsSelf(userId.toString(), validatedUserId)

    val isExit = userService.exitUser(userId)

    return if (isExit) {
      ResponseEntity.ok().build()
    } else {
      ResponseEntity.badRequest().build()
    }
  }
}
