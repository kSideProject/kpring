package kpring.server.application.port.output

import kpring.server.domain.Server

interface SaveServerPort {
  fun create(server: Server): Server
}
