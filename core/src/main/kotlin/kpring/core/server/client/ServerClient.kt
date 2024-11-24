package kpring.core.server.client

import kpring.core.global.dto.response.ApiResponse
import kpring.core.server.dto.ServerSimpleInfo
import kpring.core.server.dto.request.GetServerCondition
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.service.annotation.GetExchange

interface ServerClient {
  @GetExchange("/api/v1/server")
  fun getServerList(
    @RequestHeader("Authorization") token: String,
    @ModelAttribute condition: GetServerCondition,
  ): ResponseEntity<ApiResponse<List<ServerSimpleInfo>>>

  @GetExchange("/api/v1/server/host")
  fun getOwnedServerList(
    @RequestHeader("Authorization") token: String,
  ): ApiResponse<List<ServerSimpleInfo>>
}
