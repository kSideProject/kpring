package kpring.server.application.port.output

import kpring.core.server.dto.ServerInfo
import kpring.server.domain.Server

interface SaveServerPort {
  fun save(server: Server): ServerInfo
}
