package kpring.server.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kpring.server.util.testServer

class ServerProfileTest : DescribeSpec({

  it("hasRole 테스트") {
    // given
    val server = testServer()
    val serverProfile =
      ServerProfile(
        id = null,
        server = server,
        name = "name",
        imagePath = "/imagePath",
        userId = server.host.id,
        role = ServerRole.OWNER,
        bookmarked = false,
      )

    // when
    val hasRole = serverProfile.hasRole(ServerAuthority.INVITE)
    // then
    hasRole shouldBe true
  }
})
