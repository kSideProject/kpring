package kpring.server.service

import kpring.core.server.dto.ServerInfo
import kpring.core.server.dto.ServerSimpleInfo
import kpring.core.server.dto.request.AddUserAtServerRequest
import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.request.GetServerCondition
import kpring.core.server.dto.request.UpdateHostAtServerRequest
import kpring.core.server.dto.response.CreateServerResponse
import kpring.server.domain.Server

interface ServerService {
  fun createServer(req: CreateServerRequest): CreateServerResponse

  fun getServerInfo(serverId: String): ServerInfo

  fun getServerList(
    condition: GetServerCondition,
    userId: String,
  ): List<ServerSimpleInfo>

  fun getOwnedServerList(userId: String): List<ServerSimpleInfo>

  fun inviteUser(
    serverId: String,
    invitorId: String,
    userId: String,
  )

  fun addInvitedUser(
    serverId: String,
    req: AddUserAtServerRequest,
  )

  fun deleteServer(
    serverId: String,
    userId: String,
  )

  fun deleteServerMember(
    serverId: String,
    userId: String,
  )

  fun updateServerHost(
    serverId: String,
    userId: String,
    otherUser: UpdateHostAtServerRequest,
  )

  fun delete(serverId: String)

  fun get(id: String): Server
}
