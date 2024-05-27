package kpring.server.application.port.input

import kpring.core.server.dto.ServerInfo
import kpring.core.server.dto.ServerSimpleInfo
import kpring.core.server.dto.request.GetServerCondition

interface GetServerInfoUseCase {
  fun getServerInfo(serverId: String): ServerInfo

  fun verifyIfJoined(
    userId: String, serverId: String,
  ): Boolean

  fun getServerList(
    condition: GetServerCondition,
    userId: String,
  ): List<ServerSimpleInfo>
}
