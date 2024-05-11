package kpring.server.application.service

import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.response.CreateServerResponse
import kpring.server.application.port.input.CreateServerUseCase
import org.springframework.stereotype.Service

@Service
class ServerService : CreateServerUseCase {

  override fun run(request: CreateServerRequest): CreateServerResponse {
    return CreateServerResponse("todo", "todo")
  }
}
