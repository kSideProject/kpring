package kpring.server.application.port.output

import kpring.core.server.dto.request.GetServerCondition
import kpring.server.domain.ServerProfile

interface GetServerProfilePort {
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
