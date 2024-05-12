package kpring.server.adapter.input.rest

import kpring.core.auth.client.AuthClient
import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.dto.request.CreateServerRequest
import kpring.server.application.port.input.CreateServerUseCase
import kpring.server.application.port.input.GetServerInfoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/server")
class RestApiServerController(
  val createServerUseCase: CreateServerUseCase,
  val getServerInfoUseCase: GetServerInfoUseCase,
  val authClient: AuthClient,
) {

  @PostMapping("")
  fun createServer(
    @RequestHeader("Authorization") token: String,
    @RequestBody request: CreateServerRequest,
  ): ResponseEntity<ApiResponse<*>> {
    val data = createServerUseCase.createServer(request)
    return ResponseEntity.ok()
      .body(ApiResponse(data = data))
  }

  @GetMapping("/{serverId}")
  fun getServerInfo(
    @PathVariable serverId: String
  ): ResponseEntity<ApiResponse<*>> {
    val data = getServerInfoUseCase.getServerInfo(serverId)
    return ResponseEntity.ok()
      .body(ApiResponse(data = data))
  }

}
