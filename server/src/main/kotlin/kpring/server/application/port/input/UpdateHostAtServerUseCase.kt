package kpring.server.application.port.input

import kpring.core.server.dto.request.UpdateHostAtServerRequest

interface UpdateHostAtServerUseCase {
  fun updateServerHost(
    serverId: String,
    userId: String,
    otherUser: UpdateHostAtServerRequest,
  )
}
