package kpring.server.application.port.input

import kpring.core.server.dto.ServerInfo
import kpring.core.server.dto.ServerSimpleInfo
import kpring.core.server.dto.request.GetServerCondition

interface GetServerInfoUseCase {
  fun getServerInfo(serverId: String): ServerInfo

  fun getServerList(
    condition: GetServerCondition,
    userId: String,
  ): List<ServerSimpleInfo>
}
