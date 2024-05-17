package kpring.server.application.port.input

import kpring.core.server.dto.request.AddUserAtServerRequest

interface AddUserAtServerUseCase {
  fun inviteUser(serverId: String, userId: String)
  fun addInvitedUser(serverId: String, req: AddUserAtServerRequest)
}
