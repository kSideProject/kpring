package kpring.server.adapter.input.rest

import kpring.core.auth.client.AuthClient
import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.dto.request.CreateServerRequest
import kpring.server.application.port.input.CreateServerUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/server")
class RestApiServerController(
  val createServerUseCase: CreateServerUseCase,
  val authClient: AuthClient,
) {

  @PostMapping("")
  fun createServer(
    @RequestHeader("Authorization") token: String,
    @RequestBody request: CreateServerRequest,
  ): ResponseEntity<ApiResponse<*>> {
    val data = createServerUseCase.run(request)
    return ResponseEntity.ok()
      .body(ApiResponse(data = data))
  }
}
