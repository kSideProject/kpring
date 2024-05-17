package kpring.user.controller

import kpring.core.global.dto.response.ApiResponse
import kpring.user.dto.request.AddFriendRequest
import kpring.user.dto.response.DeleteFriendResponse
import kpring.user.dto.response.GetFriendsResponse
import kpring.user.dto.result.AddFriendResponse
import kpring.user.service.FriendService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class FriendController(val friendService: FriendService) {
  @PostMapping("/user/{userId}/friend/{friendId}")
  fun addFriend(
    @PathVariable userId: Long,
    @Validated @RequestBody request: AddFriendRequest,
  ): ResponseEntity<ApiResponse<AddFriendResponse>> {
    val response = friendService.addFriend(request, userId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @GetMapping("/user/{userId}/friends")
  fun getFriends(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
  ): ResponseEntity<ApiResponse<GetFriendsResponse>> {
    val response = friendService.getFriends(userId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }

  @DeleteMapping("/user/{userId}/friend/{friendId}")
  fun deleteFriend(
    @RequestHeader("Authorization") token: String,
    @PathVariable userId: Long,
    @PathVariable friendId: Long,
  ): ResponseEntity<ApiResponse<DeleteFriendResponse>> {
    val response = friendService.deleteFriend(userId, friendId)
    return ResponseEntity.ok(ApiResponse(data = response))
  }
}
