package kpring.server.adapter.output.mongo

import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.core.server.dto.request.GetServerCondition
import kpring.server.adapter.output.mongo.entity.QServerEntity
import kpring.server.adapter.output.mongo.entity.QServerProfileEntity
import kpring.server.adapter.output.mongo.repository.ServerProfileRepository
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.application.port.output.GetServerProfilePort
import kpring.server.domain.ServerProfile
import org.springframework.stereotype.Component

@Component
class GetServerProfileMongoImpl(
  val serverRepository: ServerRepository,
  val serverProfileRepository: ServerProfileRepository,
) : GetServerProfilePort {
  override fun get(
    serverId: String,
    userId: String,
  ): ServerProfile {
    // get server
    val serverEntity = serverRepository.findById(serverId).orElseThrow { throw ServiceException(CommonErrorCode.NOT_FOUND) }
    // get server profile
    val qProfile = QServerProfileEntity.serverProfileEntity
    val serverProfileEntity =
      serverProfileRepository.findOne(
        qProfile
          .serverId.eq(serverId)
          .and(qProfile.userId.eq(userId)),
      ).orElseThrow { throw ServiceException(CommonErrorCode.NOT_FOUND) }

    // mapping
    val server = serverEntity.toDomain()
    val serverProfile =
      ServerProfile(
        server = server,
        userId = serverProfileEntity.userId,
        name = serverProfileEntity.name,
        imagePath = serverProfileEntity.imagePath,
        role = serverProfileEntity.role,
        bookmarked = serverProfileEntity.bookmarked,
      )
    return serverProfile
  }

  override fun getProfiles(
    condition: GetServerCondition,
    userId: String,
  ): List<ServerProfile> {
    // get server profile
    val qProfile = QServerProfileEntity.serverProfileEntity
    val serverProfileEntities =
      serverProfileRepository.findAll(
        qProfile.userId.eq(userId),
      )

    // get server
    val qServer = QServerEntity.serverEntity
    val serverMap =
      mapOf(
        *serverRepository.findAll(qServer.id.`in`(serverProfileEntities.map { it.serverId }))
          .map { it.id to it }.toTypedArray(),
      )

    // mapping
    return serverProfileEntities.map {
      val serverEntity = serverMap[it.serverId]!!
      val server = serverEntity.toDomain()
      ServerProfile(
        server = server,
        userId = it.userId,
        name = it.name,
        imagePath = it.imagePath,
        role = it.role,
        bookmarked = it.bookmarked,
      )
    }
  }

  override fun getAll(serverId: String): List<ServerProfile> {
    val server =
      serverRepository.findById(serverId)
        .orElseThrow { throw ServiceException(CommonErrorCode.NOT_FOUND) }
        .toDomain()

    val qProfile = QServerProfileEntity.serverProfileEntity
    val serverProfileEntities = serverProfileRepository.findAll(qProfile.serverId.eq(serverId))

    return serverProfileEntities.map {
      ServerProfile(
        server = server,
        userId = it.userId,
        name = it.name,
        imagePath = it.imagePath,
        role = it.role,
        bookmarked = it.bookmarked,
      )
    }
  }
}
