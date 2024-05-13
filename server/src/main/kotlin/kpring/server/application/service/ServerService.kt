package kpring.server.application.service

import kpring.core.server.dto.request.CreateServerRequest
import kpring.core.server.dto.response.CreateServerResponse
import kpring.server.application.port.input.CreateServerUseCase
import kpring.server.application.port.output.SaveServerPort
import kpring.server.domain.Server
import org.springframework.stereotype.Service

@Service
class ServerService(
  val saveServerPort: SaveServerPort
) : CreateServerUseCase {

  override fun createServer(req: CreateServerRequest): CreateServerResponse {

    val server = Server(
      name = req.serverName
    )
    val serverInfo = saveServerPort.save(server)
    return CreateServerResponse(
      serverId = serverInfo.id,
      serverName = serverInfo.name
    )
  }
}
