package kpring.user.controller

import kpring.core.auth.client.AuthClient
import kpring.core.global.dto.response.ApiResponse
import kpring.core.global.exception.ServiceException
import kpring.core.server.client.ServerClient
import kpring.user.dto.request.CreateUserRequest
import kpring.user.dto.request.SearchUserRequest
import kpring.user.dto.request.UpdateUserProfileRequest
import kpring.user.dto.response.CreateUserResponse
import kpring.user.dto.response.GetUserProfileResponse
import kpring.user.dto.response.UpdateUserProfileResponse
import kpring.user.exception.UserErrorCode
import kpring.user.global.AuthValidator
import kpring.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class UserController(
  private val userService: UserService,
  private val authValidator: AuthValidator,
  private val authClient: AuthClient,
  private val serverClient: ServerClient,
) {
  @GetMapping("/user/{userId}")
  fun getUserProfile(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
  ): ResponseEntity<ApiResponse<GetUserProfileResponse>> {
    val validationResult = authClient.getTokenInfo(token)
    authValidator.checkIfAccessTokenAndGetUserId(validationResult)
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
    val validationResult = authClient.getTokenInfo(token)
    val validatedUserId = authValidator.checkIfAccessTokenAndGetUserId(validationResult)
    authValidator.checkIfUserIsSelf(userId.toString(), validatedUserId)

    val response = userService.updateProfile(userId, request, multipartFile)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @DeleteMapping("/user/{userId}")
  fun exitUser(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
  ): ResponseEntity<ApiResponse<Any>> {
    val validationResult = authClient.getTokenInfo(token)
    val validatedUserId = authValidator.checkIfAccessTokenAndGetUserId(validationResult)
    authValidator.checkIfUserIsSelf(userId.toString(), validatedUserId)

    val serverList = serverClient.getOwnedServerList(token)
    if (!serverList.data.isNullOrEmpty()) {
      throw ServiceException(UserErrorCode.SERVER_OWNER_CANNOT_LEAVE)
    }

    val response = userService.exitUser(userId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @GetMapping("/user")
  fun searchUser(
    @RequestHeader("Authorization") token: String,
    @ModelAttribute searchUserRequest: SearchUserRequest,
  ): ResponseEntity<ApiResponse<Any>> {
    val validationResult = authClient.getTokenInfo(token)
    authValidator.checkIfAccessTokenAndGetUserId(validationResult)
    val response = userService.searchUsers(searchUserRequest)
    return ResponseEntity.ok(ApiResponse(data = response))
  }
}
