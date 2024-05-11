package kpring.server.application.usecases

import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.response.CreateServerResponse

interface CreateServerUseCase {
  fun run(request : CreateServerRequest): CreateServerResponse
}
