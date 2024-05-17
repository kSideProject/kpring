package kpring.server.application.port.output

import kpring.server.domain.ServerUser

interface UpdateServerPort {

  fun addUser(serverId: String, user: ServerUser)

  fun inviteUser(serverId: String, userId: String)
}
