package kpring.server.application.port.output

import kpring.server.domain.ServerProfile

interface GetServerProfilePort {
  fun get(
    serverId: String,
    userId: String,
  ): ServerProfile

  fun getProfiles(userId: String): List<ServerProfile>

  fun getAll(serverId: String): List<ServerProfile>
}
