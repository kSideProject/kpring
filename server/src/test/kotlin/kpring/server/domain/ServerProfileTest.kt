package kpring.server.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ServerProfileTest : DescribeSpec({

  it("hasRole 테스트") {
    // given
    val ownerId = "owner"
    val user = ServerUser("invitedUserId", "userName", "profileImageUrl")
    val server =
      Server(
        id = "serverId",
        name = "serverName",
        invitedUserIds = mutableSetOf("invitedUserId"),
        users = mutableSetOf(user),
      )
    val serverProfile =
      ServerProfile(
        server = server,
        userId = ownerId,
        role = ServerRole.OWNER,
        bookmarked = false,
      )

    // when
    val hasRole = serverProfile.hasRole(ownerId, ServerAuthority.INVITE)
    // then
    hasRole shouldBe true
  }
})
