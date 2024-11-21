package kpring.server.adapter.output.mongo

import kpring.server.adapter.output.mongo.entity.ServerProfileEntity
import kpring.server.adapter.output.mongo.repository.ServerProfileRepository
import kpring.server.application.port.output.UpdateServerProfilePort
import kpring.server.domain.ServerProfile
import kpring.server.domain.ServerRole
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UpdateServerProfilePortMongoImpl(
  val serverProfileRepository: ServerProfileRepository,
  val template: MongoTemplate,
) : UpdateServerProfilePort {
  override fun updateServerHost(serverProfile: ServerProfile) {
    val newRole =
      if (serverProfile.role == ServerRole.OWNER) ServerRole.MEMBER else ServerRole.OWNER
    template.updateFirst(
      Query.query(
        Criteria.where("_id").`is`(serverProfile.server.id)
          .and("userId").`is`(serverProfile.userId)
          .and("role").`is`(serverProfile.role),
      ),
      Update().set("role", newRole),
      ServerProfileEntity::class.java,
    )

    serverProfileRepository.save(ServerProfileEntity(serverProfile))
  }
}
