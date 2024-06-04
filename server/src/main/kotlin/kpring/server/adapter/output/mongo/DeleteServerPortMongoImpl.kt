package kpring.server.adapter.output.mongo

import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.application.port.output.DeleteServerPort
import org.springframework.stereotype.Component

@Component
class DeleteServerPortMongoImpl(
  val mongoRepository: ServerRepository,
) : DeleteServerPort {
  override fun delete(serverId: String) {
    mongoRepository.deleteById(serverId)
  }
}
