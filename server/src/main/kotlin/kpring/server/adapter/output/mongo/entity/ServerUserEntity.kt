package kpring.server.adapter.output.mongo.entity

import kpring.server.domain.ServerUser

class ServerUserEntity(
  val id: String,
  val name: String,
  val profileImage: String,
) {

  fun toDomain(): ServerUser {
    return ServerUser(id, name, profileImage)
  }

  companion object {
    fun of(domainUser: ServerUser): ServerUserEntity {
      return ServerUserEntity(domainUser.id, domainUser.name, domainUser.profileImage)
    }
  }
}
