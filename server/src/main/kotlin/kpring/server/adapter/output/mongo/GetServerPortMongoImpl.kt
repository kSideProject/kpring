package kpring.server.adapter.output.mongo

import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.server.adapter.output.mongo.entity.QServerEntity
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.application.port.output.GetServerPort
import kpring.server.domain.Server
import org.springframework.stereotype.Component

@Component
class GetServerPortMongoImpl(
  val serverRepository: ServerRepository,
) : GetServerPort {
  override fun get(id: String): Server {
    val serverEntity =
      serverRepository.findById(id)
        .orElseThrow { throw ServiceException(CommonErrorCode.NOT_FOUND) }

    return Server(
      id = serverEntity.id,
      name = serverEntity.name,
      users = serverEntity.users.map { it.toDomain() }.toMutableSet(),
      invitedUserIds = serverEntity.invitedUserIds.toMutableSet(),
    )
  }

  override fun getServerWith(userId: String): List<Server> {
    val server = QServerEntity.serverEntity
    val servers =
      serverRepository.findAll(
        server.users.any().id.eq(userId),
      )

    return servers.map { entity ->
      Server(
        id = entity.id,
        name = entity.name,
        users = entity.users.map { it.toDomain() }.toMutableSet(),
        invitedUserIds = entity.invitedUserIds.toMutableSet(),
      )
    }
  }
}
