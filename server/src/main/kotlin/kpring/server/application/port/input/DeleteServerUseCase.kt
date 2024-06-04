package kpring.server.application.port.input

interface DeleteServerUseCase {
  fun deleteServer(
    serverId: String,
    userId: String,
  )
}
