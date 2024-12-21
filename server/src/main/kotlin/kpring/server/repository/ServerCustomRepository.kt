package kpring.server.repository

import kpring.core.server.dto.request.UpdateHostAtServerRequest
import kpring.server.domain.Server
import kpring.server.domain.ServerProfile

interface ServerCustomRepository {
  fun updateServerHost(
    serverId: String,
    userId: String,
    otherUser: UpdateHostAtServerRequest,
  )

  fun deleteMember(profile: ServerProfile)

  fun addUser(profile: ServerProfile)

  fun inviteUser(
    serverId: String,
    userId: String,
  )

  fun create(server: Server): Server

  fun getServerWith(userId: String): List<Server>
}
