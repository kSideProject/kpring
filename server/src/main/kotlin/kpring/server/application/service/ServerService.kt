package kpring.server.application.service

import kpring.core.server.dto.ServerInfo
import kpring.core.server.dto.ServerUserInfo
import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.response.CreateServerResponse
import kpring.server.application.port.input.CreateServerUseCase
import kpring.server.application.port.input.GetServerInfoUseCase
import kpring.server.application.port.output.GetServerPort
import kpring.server.application.port.output.SaveServerPort
import org.springframework.stereotype.Service

@Service
class ServerService(
  val saveServerPort: SaveServerPort,
  val getServer: GetServerPort,
) : CreateServerUseCase, GetServerInfoUseCase {

  override fun createServer(req: CreateServerRequest): CreateServerResponse {

    val server = saveServerPort.create(req)
    return CreateServerResponse(
      serverId = server.id,
      serverName = server.name
    )
  }

  override fun getServerInfo(serverId: String): ServerInfo {
    val server = getServer.get(serverId)
    return ServerInfo(
      id = server.id,
      name = server.name,
      users = server.users.map {
        ServerUserInfo(it.id, it.name, it.profileImage)
      }
    )
  }
}
