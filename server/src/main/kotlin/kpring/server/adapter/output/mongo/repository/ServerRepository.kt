package kpring.server.adapter.output.mongo.repository

import kpring.server.adapter.output.mongo.entity.ServerEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface ServerRepository :
  MongoRepository<ServerEntity, Long>,
  QuerydslPredicateExecutor<ServerEntity> {

}
