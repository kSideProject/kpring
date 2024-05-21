package kpring.server.adapter.output.mongo

import kpring.core.server.dto.request.CreateServerRequest
import kpring.server.adapter.output.mongo.entity.ServerEntity
import kpring.server.adapter.output.mongo.entity.ServerProfileEntity
import kpring.server.adapter.output.mongo.repository.ServerProfileRepository
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.application.port.output.SaveServerPort
import kpring.server.domain.Server
import kpring.server.domain.ServerRole
import org.springframework.stereotype.Repository

@Repository
class SaveServerPortMongoImpl(
  val serverRepository: ServerRepository,
  val serverProfileRepository: ServerProfileRepository,
) : SaveServerPort {
  override fun create(
    req: CreateServerRequest,
    userId: String,
  ): Server {
    // create server
    val serverEntity =
      serverRepository.save(ServerEntity(name = req.serverName))

    // create owner server profile
    serverProfileRepository.save(
      ServerProfileEntity(
        userId = userId,
        serverId = serverEntity.id,
        role = ServerRole.OWNER,
        bookmarked = false,
      ),
    )

    // mapping
    return Server(
      id = serverEntity.id,
      name = serverEntity.name,
      users = mutableSetOf(),
    )
  }
}
