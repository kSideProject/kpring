package kpring.server.repository

import com.querydsl.core.types.dsl.BooleanExpression
import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.core.server.dto.request.GetServerCondition
import kpring.server.domain.ServerProfile
import kpring.server.domain.ServerRole
import kpring.server.entity.QServerEntity
import kpring.server.entity.QServerProfileEntity
import kpring.server.entity.ServerProfileEntity
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class ServerProfileCustomRepositoryImpl(
  val template: MongoTemplate,
  val serverProfileRepository: ServerProfileRepository,
  val serverRepository: ServerRepository,
) : ServerProfileCustomRepository {
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

  override fun get(
    serverId: String,
    userId: String,
  ): ServerProfile {
    // get server
    val serverEntity =
      serverRepository.findById(serverId).orElseThrow {
        throw ServiceException(
          CommonErrorCode.NOT_FOUND,
        )
      }
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
        id = serverProfileEntity.id,
        server = server,
        userId = serverProfileEntity.userId,
        name = serverProfileEntity.name,
        imagePath = serverProfileEntity.imagePath,
        role = serverProfileEntity.role,
        bookmarked = serverProfileEntity.bookmarked,
      )
    return serverProfile
  }

  override fun getProfiles(
    condition: GetServerCondition,
    userId: String,
  ): List<ServerProfile> {
    // get server profile
    val qProfile = QServerProfileEntity.serverProfileEntity
    val serverProfileEntities =
      serverProfileRepository.findAll(
        qProfile.userId.eq(userId),
      )

    // get server
    val targetServerIds =
      serverProfileEntities
        .map { it.serverId!! }

    val qServer = QServerEntity.serverEntity

    val servers =
      serverRepository.findAll(
        qServer.conditionServerIds(targetServerIds, condition.serverIds),
      ).map { it.id to it }

    val serverMap = mapOf(*servers.toTypedArray())

    // mapping
    return serverProfileEntities.map {
      val serverEntity = serverMap[it.serverId]!!
      val server = serverEntity.toDomain()
      ServerProfile(
        id = serverEntity.id,
        server = server,
        userId = it.userId,
        name = it.name,
        imagePath = it.imagePath,
        role = it.role,
        bookmarked = it.bookmarked,
      )
    }
  }

  override fun getOwnedProfiles(userId: String): List<ServerProfile> {
    val qProfile = QServerProfileEntity.serverProfileEntity
    val serverProfileEntities =
      serverProfileRepository.findAll(
        qProfile.userId.eq(userId)
          .and(qProfile.role.eq(ServerRole.OWNER)),
      ).toList()

    val targetServerIds =
      serverProfileEntities
        .map { it.serverId!! }

    val qServer = QServerEntity.serverEntity

    val servers =
      serverRepository.findAll(
        qServer.id.`in`(targetServerIds),
      ).map { it.id to it }

    val serverMap = mapOf(*servers.toTypedArray())

    return serverProfileEntities.map {
      val serverEntity = serverMap[it.serverId]!!
      val server = serverEntity.toDomain()
      ServerProfile(
        id = serverEntity.id,
        server = server,
        userId = it.userId,
        name = it.name,
        imagePath = it.imagePath,
        role = it.role,
        bookmarked = it.bookmarked,
      )
    }
  }

  override fun getAll(serverId: String): List<ServerProfile> {
    val server =
      serverRepository.findById(serverId)
        .orElseThrow { throw ServiceException(CommonErrorCode.NOT_FOUND) }
        .toDomain()

    val qProfile = QServerProfileEntity.serverProfileEntity
    val serverProfileEntities = serverProfileRepository.findAll(qProfile.serverId.eq(serverId))

    return serverProfileEntities.map {
      ServerProfile(
        id = it.id,
        server = server,
        userId = it.userId,
        name = it.name,
        imagePath = it.imagePath,
        role = it.role,
        bookmarked = it.bookmarked,
      )
    }
  }

  /**
   * serverIds 목록 중에서 condition에 포함되는 serverId만 검색한다.
   * 만약 condition이 없는 경우에는 serverIds 목록 전체를 검색한다.
   */
  private fun QServerEntity.conditionServerIds(
    serverIds: List<String>,
    condition: Collection<String>?,
  ): BooleanExpression {
    if (!condition.isNullOrEmpty()) {
      return this.id.`in`(serverIds.filter { condition.contains(it) })
    }
    return this.id.`in`(serverIds)
  }
}
