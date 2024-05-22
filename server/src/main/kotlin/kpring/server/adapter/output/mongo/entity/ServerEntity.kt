package kpring.server.adapter.output.mongo.entity

import kpring.server.domain.Server
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "server")
class ServerEntity(
  var name: String,
  var users: MutableList<String> = mutableListOf(),
  var invitedUserIds: MutableList<String> = mutableListOf(),
) {
  @Id
  lateinit var id: String

  fun toDomain(): Server {
    return Server(
      id = id,
      name = name,
      users = users.toMutableSet(),
      invitedUserIds = invitedUserIds.toMutableSet(),
    )
  }
}
