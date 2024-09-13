package kpring.server.adapter.output.mongo.repository

import kpring.server.adapter.output.mongo.entity.ServerProfileEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface ServerProfileRepository :
  MongoRepository<ServerProfileEntity, String>,
  QuerydslPredicateExecutor<ServerProfileEntity> {
  fun deleteByServerId(serverId: String)
}
