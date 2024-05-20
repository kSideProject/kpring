package kpring.server.adapter.output.mongo.entity

import com.querydsl.core.annotations.QueryEntity
import kpring.server.domain.Server
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@QueryEntity
@Document(collection = "server")
class ServerEntity(
  var name: String,
  var users: MutableList<ServerUserEntity> = mutableListOf(),
  var invitedUserIds: MutableList<String> = mutableListOf(),
) {
  @Id
  lateinit var id: String

  companion object {
    fun of(server: Server): ServerEntity {
      return ServerEntity(
        name = server.name,
        users = server.users.map(ServerUserEntity::of).toMutableList(),
        invitedUserIds = server.invitedUserIds.toMutableList(),
      )
    }
  }
}
