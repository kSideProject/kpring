package kpring.server.application.usecases

import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.response.CreateServerResponse
import org.springframework.stereotype.Service

@Service
class ServerService : CreateServerUseCase {

  override fun run(request: CreateServerRequest): CreateServerResponse {
    TODO("Not yet implemented")
  }
}
