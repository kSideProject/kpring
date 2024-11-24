package kpring.server.adapter.output.mongo

import kpring.server.adapter.output.mongo.entity.ServerEntity
import kpring.server.adapter.output.mongo.repository.ServerProfileRepository
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.application.port.output.DeleteServerPort
import kpring.server.domain.ServerProfile
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

@Component
class DeleteServerPortMongoImpl(
  val mongoRepository: ServerRepository,
  val mongoServerProfileRepository: ServerProfileRepository,
  val template: MongoTemplate,
) : DeleteServerPort {
  override fun delete(serverId: String) {
    mongoRepository.deleteById(serverId)
    mongoServerProfileRepository.deleteByServerId(serverId)
  }

  override fun deleteMember(profile: ServerProfile) {
    template.updateFirst(
      query(
        where("_id").`is`(profile.server.id)
          .and("users").`in`(profile.userId),
      ),
      Update()
        .pull("users", profile.userId),
      ServerEntity::class.java,
    )

    mongoServerProfileRepository.deleteByServerIdAndUserId(profile.server.id, profile.userId)
  }
}
