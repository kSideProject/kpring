package kpring.server.application.port.input

import kpring.core.server.dto.ServerInfo
import kpring.core.server.dto.ServerSimpleInfo

interface GetServerInfoUseCase {
  fun getServerInfo(serverId: String): ServerInfo

  fun getServerList(userId: String): List<ServerSimpleInfo>
}
