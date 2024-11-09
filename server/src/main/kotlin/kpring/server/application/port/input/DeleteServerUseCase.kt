package kpring.server.application.port.input

interface DeleteServerUseCase {
  fun deleteServer(
    serverId: String,
    userId: String,
  )

  fun deleteServerMember(
    serverId: String,
    userId: String,
  )
}
