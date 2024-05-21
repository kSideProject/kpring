package kpring.server.adapter.output.mongo

import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
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
        role = serverProfileEntity.role,
        bookmarked = serverProfileEntity.bookmarked,
      )
    return serverProfile
  }
}
