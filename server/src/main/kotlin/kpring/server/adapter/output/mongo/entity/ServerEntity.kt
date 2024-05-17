package kpring.server.adapter.output.mongo.entity

import jakarta.persistence.Entity
import kpring.server.domain.Server
import kpring.server.domain.ServerRole
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Entity
@Document(collection = "server")
class ServerEntity(
  var name: String,
  var users: MutableList<ServerUserEntity> = mutableListOf(),
  var invitedUserIds: MutableList<String> = mutableListOf(),
  var authorities: MutableMap<String, ServerRole> = mutableMapOf(),
) {
  @Id
  lateinit var id: String

  companion object {
    fun of(server: Server): ServerEntity{
      return ServerEntity(
        name = server.name,
        users = server.users.map(ServerUserEntity::of).toMutableList(),
        invitedUserIds = server.invitedUserIds.toMutableList()
      )
    }
  }
}
