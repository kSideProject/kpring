package kpring.user.controller

import kpring.core.global.dto.response.ApiResponse
import kpring.user.dto.response.DeleteFriendResponse
import kpring.user.dto.response.GetFriendsResponse
import kpring.user.dto.result.AddFriendResponse
import kpring.user.global.AuthValidator
import kpring.user.service.FriendService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class FriendController(
  private val friendService: FriendService,
  private val authValidator: AuthValidator,
) {
  @PostMapping("/user/{userId}/friend/{friendId}")
  fun addFriend(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
    @PathVariable friendId: Long,
  ): ResponseEntity<ApiResponse<AddFriendResponse>> {
    val validatedUserId = authValidator.checkIfAccessTokenAndGetUserId(token)
    authValidator.checkIfUserIsSelf(userId.toString(), validatedUserId)
    val response = friendService.addFriend(userId, friendId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @GetMapping("/user/{userId}/friends")
  fun getFriends(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
  ): ResponseEntity<ApiResponse<GetFriendsResponse>> {
    authValidator.checkIfAccessTokenAndGetUserId(token)
    val response = friendService.getFriends(userId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @DeleteMapping("/user/{userId}/friend/{friendId}")
  fun deleteFriend(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
    @PathVariable friendId: Long,
  ): ResponseEntity<ApiResponse<DeleteFriendResponse>> {
    val validatedUserId = authValidator.checkIfAccessTokenAndGetUserId(token)
    authValidator.checkIfUserIsSelf(userId.toString(), validatedUserId)
    val response = friendService.deleteFriend(userId, friendId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }
}
