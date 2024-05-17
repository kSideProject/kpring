package kpring.server.application.service

import kpring.core.server.dto.ServerInfo
import kpring.core.server.dto.ServerUserInfo
import kpring.core.server.dto.request.AddUserAtServerRequest
import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.response.CreateServerResponse
import kpring.server.application.port.input.AddUserAtServerUseCase
import kpring.server.application.port.input.CreateServerUseCase
import kpring.server.application.port.input.GetServerInfoUseCase
import kpring.server.application.port.output.SaveServerPort
import kpring.server.application.port.output.GetServerPort
import kpring.server.application.port.output.UpdateServerPort
import kpring.server.domain.ServerUser
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ServerService(
  val createServerPort: SaveServerPort,
  val getServer: GetServerPort,
  val updateServerPort: UpdateServerPort,
) : CreateServerUseCase, GetServerInfoUseCase, AddUserAtServerUseCase {

  override fun createServer(req: CreateServerRequest): CreateServerResponse {

    val server = createServerPort.create(req)
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

  @Transactional
  override fun inviteUser(serverId: String, userId: String) {
    val server = getServer.get(serverId)
    server.registerInvitation(userId)
    updateServerPort.inviteUser(server.id, userId)
  }

  @Transactional
  override fun addInvitedUser(serverId: String, req: AddUserAtServerRequest) {
    val server = getServer.get(serverId)
    val user = ServerUser(req.userId, req.userName, req.profileImage)
    server.addUser(user)
    updateServerPort.addUser(server.id, user)
  }
}
