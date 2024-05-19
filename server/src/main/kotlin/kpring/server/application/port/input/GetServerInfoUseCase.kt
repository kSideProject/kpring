package kpring.server.application.port.input

import kpring.core.server.dto.ServerInfo

interface GetServerInfoUseCase {
  fun getServerInfo(serverId: String): ServerInfo
}
