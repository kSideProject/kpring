package kpring.server.adapter.input.rest

import kpring.core.auth.client.AuthClient
import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.dto.request.CreateServerRequest
import kpring.server.application.port.input.CreateServerUseCase
import kpring.server.application.port.input.GetServerInfoUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


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

  @GetMapping
  fun getServerInfo() : ResponseEntity<ApiResponse<*>> {
    val data = getServerInfoUseCase.getServerInfo()
    return ResponseEntity.ok()
      .body(ApiResponse(data = data))
  }

}
