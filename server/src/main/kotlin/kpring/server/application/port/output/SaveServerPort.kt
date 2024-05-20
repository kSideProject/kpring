package kpring.server.application.port.output

import kpring.core.server.dto.request.CreateServerRequest
import kpring.server.domain.Server

interface SaveServerPort {
  fun create(req: CreateServerRequest, userId: String): Server
}
