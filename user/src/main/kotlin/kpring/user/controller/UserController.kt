package kpring.user.controller

import kpring.core.auth.client.AuthClient
import kpring.core.auth.enums.TokenType
import kpring.core.global.dto.response.ApiResponse
import kpring.core.global.exception.ServiceException
import kpring.user.dto.request.CreateUserRequest
import kpring.user.dto.request.UpdateUserProfileRequest
import kpring.user.dto.response.CreateUserResponse
import kpring.user.dto.response.GetUserProfileResponse
import kpring.user.dto.response.UpdateUserProfileResponse
import kpring.user.exception.UserErrorCode
import kpring.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class UserController(
  val userService: UserService,
  val authClient: AuthClient,
) {
  @GetMapping("/user/{userId}")
  fun getUserProfile(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
  ): ResponseEntity<ApiResponse<GetUserProfileResponse>> {
    checkIfAccessTokenAndGetUserId(token)
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
    val validatedUserId = checkIfAccessTokenAndGetUserId(token)
    checkIfUserIsSelf(userId.toString(), validatedUserId)

    val response = userService.updateProfile(userId, request, multipartFile)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @DeleteMapping("/user/{userId}")
  fun exitUser(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
  ): ResponseEntity<ApiResponse<Any>> {
    val validatedUserId = checkIfAccessTokenAndGetUserId(token)
    checkIfUserIsSelf(userId.toString(), validatedUserId)

    val isExit = userService.exitUser(userId)

    return if (isExit) {
      ResponseEntity.ok().build()
    } else {
      ResponseEntity.badRequest().build()
    }
  }

  private fun checkIfAccessTokenAndGetUserId(token: String): String {
    val validationResult = authClient.getTokenInfo(token)
    if (validationResult.data!!.type != TokenType.ACCESS) {
      throw ServiceException(UserErrorCode.BAD_REQUEST)
    }

    return validationResult.data!!.userId
  }

  private fun checkIfUserIsSelf(
    userId: String,
    validatedUserId: String,
  ) {
    if (userId != validatedUserId) {
      throw ServiceException(UserErrorCode.NOT_ALLOWED)
    }
  }
}
