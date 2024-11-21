package kpring.server.application.port.output

import kpring.server.domain.ServerProfile

interface UpdateServerProfilePort {
  fun updateServerHost(serverProfile: ServerProfile)
}
