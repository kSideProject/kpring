package kpring.server.entity

import kpring.server.domain.ServerProfile
import kpring.server.domain.ServerRole
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("server_profile")
class ServerProfileEntity(
  @Id
  val id: String?,
  val userId: String,
  val name: String,
  val imagePath: String,
  val serverId: String?,
  val role: ServerRole,
  val bookmarked: Boolean,
) {
  constructor(profile: ServerProfile) : this(
    id = profile.id,
    userId = profile.userId,
    name = profile.name,
    imagePath = profile.imagePath,
    serverId = profile.server.id,
    role = profile.role,
    bookmarked = profile.bookmarked,
  )
}
