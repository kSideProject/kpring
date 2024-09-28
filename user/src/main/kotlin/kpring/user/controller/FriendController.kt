package kpring.user.controller

import kpring.core.auth.client.AuthClient
import kpring.core.global.dto.response.ApiResponse
import kpring.user.dto.response.AddFriendResponse
import kpring.user.dto.response.DeleteFriendResponse
import kpring.user.dto.response.GetFriendRequestsResponse
import kpring.user.dto.response.GetFriendsResponse
import kpring.user.global.AuthValidator
import kpring.user.service.FriendService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class FriendController(
  private val friendService: FriendService,
  private val authValidator: AuthValidator,
  private val authClient: AuthClient,
) {
  @GetMapping("/user/{userId}/requests")
  fun getFriendRequests(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
  ): ResponseEntity<ApiResponse<GetFriendRequestsResponse>> {
    val validationResult = authClient.getTokenInfo(token)
    val validatedUserId = authValidator.checkIfAccessTokenAndGetUserId(validationResult)
    authValidator.checkIfUserIsSelf(userId.toString(), validatedUserId)
    val response = friendService.getFriendRequests(userId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @GetMapping("/user/{userId}/friends")
  fun getFriends(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
  ): ResponseEntity<ApiResponse<GetFriendsResponse>> {
    val validationResult = authClient.getTokenInfo(token)
    authValidator.checkIfAccessTokenAndGetUserId(validationResult)
    val response = friendService.getFriends(userId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @PostMapping("/user/{userId}/friend/{friendId}")
  fun addFriend(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
    @PathVariable friendId: Long,
  ): ResponseEntity<ApiResponse<AddFriendResponse>> {
    val validationResult = authClient.getTokenInfo(token)
    val validatedUserId = authValidator.checkIfAccessTokenAndGetUserId(validationResult)
    authValidator.checkIfUserIsSelf(userId.toString(), validatedUserId)
    val response = friendService.addFriend(userId, friendId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @PatchMapping("/user/{userId}/friend/{friendId}")
  fun acceptFriendRequest(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
    @PathVariable friendId: Long,
  ): ResponseEntity<ApiResponse<AddFriendResponse>> {
    val validationResult = authClient.getTokenInfo(token)
    val validatedUserId = authValidator.checkIfAccessTokenAndGetUserId(validationResult)
    authValidator.checkIfUserIsSelf(userId.toString(), validatedUserId)
    val response = friendService.acceptFriendRequest(userId, friendId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @DeleteMapping("/user/{userId}/friend/{friendId}")
  fun deleteFriend(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
    @PathVariable friendId: Long,
  ): ResponseEntity<ApiResponse<DeleteFriendResponse>> {
    val validationResult = authClient.getTokenInfo(token)
    val validatedUserId = authValidator.checkIfAccessTokenAndGetUserId(validationResult)
    authValidator.checkIfUserIsSelf(userId.toString(), validatedUserId)
    val response = friendService.deleteFriend(userId, friendId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }
}
