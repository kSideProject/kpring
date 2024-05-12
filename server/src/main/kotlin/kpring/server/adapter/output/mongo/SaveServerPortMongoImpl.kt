package kpring.server.adapter.output.mongo

import kpring.core.server.dto.ServerInfo
import kpring.server.adapter.output.mongo.entity.ServerEntity
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.application.port.output.SaveServerPort
import kpring.server.domain.Server
import org.springframework.stereotype.Repository

@Repository
class SaveServerPortMongoImpl(
  val serverRepository: ServerRepository
) : SaveServerPort {
  override fun save(server: Server): ServerInfo {
    val entity = serverRepository.save(
      ServerEntity(
        name = server.name
      )
    )
    return ServerInfo(
      id = entity.id!!.toString(),
      name = entity.name!!,
      users = emptyList(),
    )
  }
}
