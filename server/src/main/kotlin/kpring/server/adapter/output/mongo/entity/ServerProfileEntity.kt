package kpring.server.adapter.output.mongo.entity

import kpring.server.domain.ServerRole
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("server_profile")
class ServerProfileEntity(
  @Id
  val userId: String,
  val name: String,
  val imagePath: String,
  val serverId: String,
  val role: ServerRole,
  val bookmarked: Boolean,
)
