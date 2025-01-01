package kpring.server.repository

import kpring.server.entity.ServerEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface ServerRepository :
  MongoRepository<ServerEntity, String>,
  QuerydslPredicateExecutor<ServerEntity>
