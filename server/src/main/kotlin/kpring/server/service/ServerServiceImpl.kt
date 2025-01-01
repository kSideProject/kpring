package kpring.server.service

import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.core.server.dto.ServerInfo
import kpring.core.server.dto.ServerSimpleInfo
import kpring.core.server.dto.ServerUserInfo
import kpring.core.server.dto.request.AddUserAtServerRequest
import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.request.GetServerCondition
import kpring.core.server.dto.request.UpdateHostAtServerRequest
import kpring.core.server.dto.response.CreateServerResponse
import kpring.server.domain.Category
import kpring.server.domain.Server
import kpring.server.domain.ServerAuthority
import kpring.server.domain.ServerRole
import kpring.server.repository.ServerCustomRepository
import kpring.server.repository.ServerProfileCustomRepository
import kpring.server.repository.ServerProfileRepository
import kpring.server.repository.ServerRepository
import kpring.server.util.toInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ServerServiceImpl(
  val serverRepository: ServerRepository,
  val serverProfileRepository: ServerProfileRepository,
  val serverCustomRepository: ServerCustomRepository,
  val serverProfileCustomRepository: ServerProfileCustomRepository,
) : ServerService {
  override fun createServer(req: CreateServerRequest): CreateServerResponse {
    val server =
      serverCustomRepository.create(
        Server(
          name = req.serverName,
          users = mutableSetOf(req.userId),
          theme = req.theme,
          categories = req.categories,
          hostName = req.hostName,
          hostId = req.userId,
        ),
      )
    return CreateServerResponse(
      serverId = server.id!!,
      serverName = server.name,
      theme = server.theme.toInfo(),
      hostName = server.host.name,
      categories = server.categories.map(Category::toInfo),
    )
  }

  override fun getServerInfo(serverId: String): ServerInfo {
    val server = get(serverId)
    val serverProfiles = serverProfileCustomRepository.getAll(server.id!!)
    return ServerInfo(
      id = server.id,
      name = server.name,
      users =
        serverProfiles.map { profile ->
          ServerUserInfo(
            id = profile.userId,
            name = profile.name,
            profileImage = profile.imagePath,
          )
        },
      theme = server.theme.toInfo(),
      categories = server.categories.map(Category::toInfo),
    )
  }

  override fun getServerList(
    condition: GetServerCondition,
    userId: String,
  ): List<ServerSimpleInfo> {
    return serverProfileCustomRepository.getProfiles(condition, userId)
      .map { profile ->
        ServerSimpleInfo(
          id = profile.server.id!!,
          name = profile.server.name,
          bookmarked = profile.bookmarked,
          categories = profile.server.categories.map(Category::toInfo),
          theme = profile.server.theme.toInfo(),
          hostName = profile.server.host.name,
        )
      }
  }

  override fun getOwnedServerList(userId: String): List<ServerSimpleInfo> {
    return serverProfileCustomRepository.getOwnedProfiles(userId)
      .map { profile ->
        ServerSimpleInfo(
          id = profile.server.id!!,
          name = profile.server.name,
          hostName = profile.server.host.name,
          bookmarked = profile.bookmarked,
          categories = profile.server.categories.map(Category::toInfo),
          theme = profile.server.theme.toInfo(),
        )
      }
  }

  @Transactional
  override fun inviteUser(
    serverId: String,
    invitorId: String,
    userId: String,
  ) {
    // validate invitor authority
    val serverProfile = serverProfileCustomRepository.get(serverId, invitorId)
    if (serverProfile.dontHasRole(ServerAuthority.INVITE)) {
      throw ServiceException(CommonErrorCode.FORBIDDEN)
    }
    // register invitation
    val server = serverProfile.server
    server.registerInvitation(userId)
    serverCustomRepository.inviteUser(server.id!!, userId)
  }

  @Transactional
  override fun addInvitedUser(
    serverId: String,
    req: AddUserAtServerRequest,
  ) {
    val server = get(serverId)
    val profile = server.addUser(req.userId, req.userName, req.profileImage)
    serverCustomRepository.addUser(profile)
  }

  override fun deleteServer(
    serverId: String,
    userId: String,
  ) {
    val serverProfile = serverProfileCustomRepository.get(serverId, userId)
    if (serverProfile.dontHasRole(ServerAuthority.DELETE)) {
      throw ServiceException(CommonErrorCode.FORBIDDEN)
    }
    delete(serverId)
  }

  override fun deleteServerMember(
    serverId: String,
    userId: String,
  ) {
    val serverProfile = serverProfileCustomRepository.get(serverId, userId)
    if (serverProfile.role == ServerRole.OWNER) {
      throw ServiceException(CommonErrorCode.FORBIDDEN)
    }
    serverCustomRepository.deleteMember(serverProfile)
  }

  override fun updateServerHost(
    serverId: String,
    userId: String,
    otherUser: UpdateHostAtServerRequest,
  ) {
    val hostServerProfile = serverProfileCustomRepository.get(serverId, userId)
    val newHostServerProfile = serverProfileCustomRepository.get(serverId, otherUser.userId)

    if (hostServerProfile.role != ServerRole.OWNER) {
      throw ServiceException(CommonErrorCode.FORBIDDEN)
    }

    val server = hostServerProfile.server
    server.updateServerHost(otherUser.userId, otherUser.userName)
    serverCustomRepository.updateServerHost(serverId, userId, otherUser)

    hostServerProfile.updateServerHost(hostServerProfile)
    newHostServerProfile.updateServerHost(newHostServerProfile)
    serverProfileCustomRepository.updateServerHost(hostServerProfile)
    serverProfileCustomRepository.updateServerHost(newHostServerProfile)
  }

  override fun delete(serverId: String) {
    serverRepository.deleteById(serverId)
    serverProfileRepository.deleteByServerId(serverId)
  }

  override fun get(id: String): Server {
    val serverEntity =
      serverRepository.findById(id)
        .orElseThrow { throw ServiceException(CommonErrorCode.NOT_FOUND) }

    return serverEntity.toDomain()
  }
}
