package kpring.server.application.port.output

import kpring.core.server.dto.request.UpdateHostAtServerRequest
import kpring.server.domain.ServerProfile

interface UpdateServerPort {
  fun addUser(profile: ServerProfile)

  fun inviteUser(
    serverId: String,
    userId: String,
  )

  fun updateServerHost(
    serverId: String,
    userId: String,
    otherUser: UpdateHostAtServerRequest,
  )
}
