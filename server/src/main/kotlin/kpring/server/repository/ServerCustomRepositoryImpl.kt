package kpring.server.repository

import kpring.core.server.dto.request.UpdateHostAtServerRequest
import kpring.server.domain.Server
import kpring.server.domain.ServerProfile
import kpring.server.domain.ServerRole
import kpring.server.entity.QServerEntity
import kpring.server.entity.ServerEntity
import kpring.server.entity.ServerProfileEntity
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ServerCustomRepositoryImpl(
  val template: MongoTemplate,
  val serverProfileRepository: ServerProfileRepository,
  val serverRepository: ServerRepository,
) : ServerCustomRepository {
  override fun updateServerHost(
    serverId: String,
    userId: String,
    otherUser: UpdateHostAtServerRequest,
  ) {
    template.updateFirst(
      Query.query(
        Criteria.where("_id").`is`(serverId)
          .and("hostId").`is`(userId),
      ),
      Update().set("hostId", otherUser.userId)
        .set("hostName", otherUser.userName),
      ServerEntity::class.java,
    )
  }

  override fun deleteMember(profile: ServerProfile) {
    template.updateFirst(
      Query.query(
        Criteria.where("_id").`is`(profile.server.id)
          .and("users").`in`(profile.userId),
      ),
      Update()
        .pull("users", profile.userId),
      ServerEntity::class.java,
    )

    serverProfileRepository.deleteByServerIdAndUserId(profile.server.id, profile.userId)
  }

  override fun addUser(profile: ServerProfile) {
    template.updateFirst(
      Query.query(
        Criteria.where("_id").`is`(profile.server.id)
          .and("invitedUserIds").`in`(profile.userId),
      ),
      Update()
        .pull("invitedUserIds", profile.userId)
        .push("users", profile.userId),
      ServerEntity::class.java,
    )

    serverProfileRepository.save(ServerProfileEntity(profile))
  }

  override fun inviteUser(
    serverId: String,
    userId: String,
  ) {
    template.updateFirst(
      Query.query(
        Criteria.where("_id").`is`(serverId)
          .and("invitedUserIds").nin(userId),
      ),
      Update().push("invitedUserIds").value(userId),
      ServerEntity::class.java,
    )
  }

  @Value("\${resource.default.profileImagePath}")
  private lateinit var defaultImagePath: String

  override fun create(server: Server): Server {
    // create server
    val serverEntity = serverRepository.save(ServerEntity(server))

    // create owner server profile
    serverProfileRepository.save(
      ServerProfileEntity(
        id = null,
        userId = server.host.id,
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

  override fun getServerWith(userId: String): List<Server> {
    val server = QServerEntity.serverEntity
    val servers =
      serverRepository.findAll(
        server.users.any().eq(userId),
      )

    return servers.map(ServerEntity::toDomain)
  }
}
