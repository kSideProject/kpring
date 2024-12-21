package kpring.server.repository

import kpring.core.server.dto.request.GetServerCondition
import kpring.server.domain.ServerProfile

interface ServerProfileCustomRepository {
  fun updateServerHost(serverProfile: ServerProfile)

  fun get(
    serverId: String,
    userId: String,
  ): ServerProfile

  fun getProfiles(
    condition: GetServerCondition,
    userId: String,
  ): List<ServerProfile>

  fun getOwnedProfiles(userId: String): List<ServerProfile>

  fun getAll(serverId: String): List<ServerProfile>
}
