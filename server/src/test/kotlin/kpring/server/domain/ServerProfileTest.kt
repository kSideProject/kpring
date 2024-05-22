package kpring.server.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ServerProfileTest : DescribeSpec({

  it("hasRole 테스트") {
    // given
    val ownerId = "owner"
    val userId = "invitedUserId"
    val server =
      Server(
        id = "serverId",
        name = "serverName",
        invitedUserIds = mutableSetOf("invitedUserId"),
        users = mutableSetOf(userId),
      )
    val serverProfile =
      ServerProfile(
        server = server,
        name = "name",
        imagePath = "/imagePath",
        userId = ownerId,
        role = ServerRole.OWNER,
        bookmarked = false,
      )

    // when
    val hasRole = serverProfile.hasRole(ServerAuthority.INVITE)
    // then
    hasRole shouldBe true
  }
})
