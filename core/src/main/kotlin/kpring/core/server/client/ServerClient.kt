package kpring.core.server.client

import org.springframework.web.service.annotation.GetExchange

interface ServerClient {
  @GetExchange("/api/v1/server/{serverId}/user/{userId}")
  fun verifyIfJoined(
    userId: String,
    serverId: String,
  ): Boolean
}
