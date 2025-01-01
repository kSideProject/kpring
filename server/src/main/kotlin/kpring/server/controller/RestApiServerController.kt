package kpring.server.controller

import kpring.core.auth.client.AuthClient
import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.dto.ServerInfo
import kpring.core.server.dto.ServerSimpleInfo
import kpring.core.server.dto.request.AddUserAtServerRequest
import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.request.GetServerCondition
import kpring.core.server.dto.request.UpdateHostAtServerRequest
import kpring.server.service.ServerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/server")
class RestApiServerController(
  val serverService: ServerService,
  val authClient: AuthClient,
) {
  @PostMapping("")
  fun createServer(
    @RequestHeader("Authorization") token: String,
    @RequestBody request: CreateServerRequest,
  ): ResponseEntity<ApiResponse<Any>> {
    // get and validate user token
    val userInfo = authClient.getTokenInfo(token).data!!

    // validate userId
    if (userInfo.userId != request.userId) {
      return ResponseEntity.badRequest()
        .body(ApiResponse(message = "유저 정보가 일치하지 않습니다"))
    }

    // logic
    val data = serverService.createServer(request)

    return ResponseEntity.ok()
      .body(ApiResponse(data = data))
  }

  @GetMapping
  fun getServerList(
    @RequestHeader("Authorization") token: String,
    @ModelAttribute condition: GetServerCondition,
  ): ResponseEntity<ApiResponse<List<ServerSimpleInfo>>> {
    val userInfo = authClient.getTokenInfo(token).data!!
    val data = serverService.getServerList(condition, userInfo.userId)
    return ResponseEntity.ok()
      .body(ApiResponse(data = data))
  }

  @GetMapping("/host")
  fun getOwnedServerList(
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<ApiResponse<List<ServerSimpleInfo>>> {
    val userInfo = authClient.getTokenInfo(token).data!!
    val data = serverService.getOwnedServerList(userInfo.userId)
    return ResponseEntity.ok()
      .body(ApiResponse(data = data))
  }

  @GetMapping("/{serverId}")
  fun getServerInfo(
    @PathVariable serverId: String,
  ): ResponseEntity<ApiResponse<ServerInfo>> {
    val data = serverService.getServerInfo(serverId)
    return ResponseEntity.ok()
      .body(ApiResponse(data = data))
  }

  @PutMapping("/{serverId}/invitation/{userId}")
  fun inviteUser(
    @PathVariable serverId: String,
    @PathVariable userId: String,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<Any> {
    val invitor = authClient.getTokenInfo(token).data!!
    serverService.inviteUser(serverId, invitor.userId, userId)
    return ResponseEntity.ok().build()
  }

  @PutMapping("/{serverId}/user")
  fun addUser(
    @PathVariable serverId: String,
    @RequestHeader("Authorization") token: String,
    @RequestBody request: AddUserAtServerRequest,
  ): ResponseEntity<Any> {
    serverService.addInvitedUser(serverId, request)
    return ResponseEntity.ok().build()
  }

  @DeleteMapping("/{serverId}")
  fun deleteServer(
    @PathVariable serverId: String,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<Any> {
    val userInfo = authClient.getTokenInfo(token).data!!
    serverService.deleteServer(serverId, userInfo.userId)
    return ResponseEntity.ok().build()
  }

  @DeleteMapping("/{serverId}/exit")
  fun exitServer(
    @PathVariable serverId: String,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<Any> {
    val userInfo = authClient.getTokenInfo(token).data!!
    serverService.deleteServerMember(serverId, userInfo.userId)
    return ResponseEntity.ok().build()
  }

  @PatchMapping("/{serverId}")
  fun updateServerHost(
    @PathVariable serverId: String,
    @RequestBody otherUser: UpdateHostAtServerRequest,
    @RequestHeader("Authorization") token: String,
  ): ResponseEntity<Any> {
    val userInfo = authClient.getTokenInfo(token).data!!
    serverService.updateServerHost(serverId, userInfo.userId, otherUser)
    return ResponseEntity.ok().build()
  }
}
