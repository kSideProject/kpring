package kpring.server.adapter.output.mongo

import kpring.server.adapter.output.mongo.entity.ServerEntity
import kpring.server.adapter.output.mongo.entity.ServerProfileEntity
import kpring.server.adapter.output.mongo.repository.ServerProfileRepository
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.application.port.output.SaveServerPort
import kpring.server.domain.Server
import kpring.server.domain.ServerRole
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class SaveServerPortMongoImpl(
  val serverRepository: ServerRepository,
  val serverProfileRepository: ServerProfileRepository,
) : SaveServerPort {
  @Value("\${resource.default.profileImagePath}")
  private lateinit var defaultImagePath: String

  override fun create(server: Server): Server {
    // create server
    val serverEntity = serverRepository.save(ServerEntity(server))

    // create owner server profile
    serverProfileRepository.save(
      ServerProfileEntity(
        id = null,
        userId = server.users.first(),
        // todo change
        name = "USER_${UUID.randomUUID()}",
        // todo change
        imagePath = defaultImagePath,
        serverId = serverEntity.id,
        role = ServerRole.OWNER,
        bookmarked = false,
      ),
    )

    // mapping
    return serverEntity.toDomain()
  }
}
