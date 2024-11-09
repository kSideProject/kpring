package kpring.server.application.port.output

import kpring.server.domain.ServerProfile

interface DeleteServerPort {
  fun delete(serverId: String)

  fun deleteMember(serverProfile: ServerProfile)
}
