package kpring.server.application.port.input

import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.response.CreateServerResponse

interface CreateServerUseCase {
  fun createServer(request : CreateServerRequest): CreateServerResponse
}
