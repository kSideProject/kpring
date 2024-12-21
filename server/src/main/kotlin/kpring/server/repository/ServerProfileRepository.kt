package kpring.server.repository

import kpring.server.entity.ServerProfileEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface ServerProfileRepository :
  MongoRepository<ServerProfileEntity, String>,
  QuerydslPredicateExecutor<ServerProfileEntity> {
  fun deleteByServerId(serverId: String)

  fun deleteByServerIdAndUserId(
    serverId: String?,
    userId: String,
  )
}
