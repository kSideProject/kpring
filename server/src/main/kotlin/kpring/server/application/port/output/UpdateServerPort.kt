package kpring.server.application.port.output

import kpring.server.domain.ServerProfile

interface UpdateServerPort {
  fun addUser(profile: ServerProfile)

  fun inviteUser(
    serverId: String,
    userId: String,
  )
}
