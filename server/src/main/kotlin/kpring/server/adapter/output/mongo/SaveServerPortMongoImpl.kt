package kpring.server.adapter.output.mongo

import kpring.core.server.dto.request.CreateServerRequest
import kpring.server.adapter.output.mongo.entity.ServerEntity
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.application.port.output.SaveServerPort
import kpring.server.domain.Server
import kpring.server.domain.ServerRole
import org.springframework.stereotype.Repository

@Repository
class SaveServerPortMongoImpl(
  val serverRepository: ServerRepository,
) : SaveServerPort {
  override fun create(
    req: CreateServerRequest,
    userId: String,
  ): Server {
    val entity =
      serverRepository.save(
        ServerEntity(
          name = req.serverName,
          authorities = mutableMapOf(userId to ServerRole.OWNER),
        ),
      )
    return Server(
      id = entity.id,
      name = entity.name,
      users = mutableSetOf(),
      authorities = entity.authorities,
    )
  }
}
