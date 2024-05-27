package kpring.server.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotContain
import io.kotest.matchers.shouldBe
import kpring.core.global.exception.ServiceException
import kpring.server.error.ServerErrorCode

/**
 * Server 도메인 단위 테스트입니다.
 */
class ServerTest : DescribeSpec({

  it("초대된 유저라면 서버에 유저가 가입할 때, 초대 목록에서 제거하고 가입 목록에 추가한다.") {
    // given
    val server =
      Server(
        "serverId",
        "serverName",
        mutableSetOf(),
        invitedUserIds = mutableSetOf("invitedUserId"),
      )
    val user = ServerUser("invitedUserId", "userName", "profileImageUrl")

    // when
    server.addUser(user)

    // then
    server.invitedUserIds shouldNotContain user.id
    server.users shouldContain user
  }

  it("이미 등록된 유저를 초대시 예외가 발생한다.") {
    // given
    val server =
      Server(
        "serverId",
        "serverName",
        mutableSetOf(),
        invitedUserIds = mutableSetOf("invitedUserId"),
      )
    val user = ServerUser("invitedUserId", "userName", "profileImageUrl")
    server.addUser(user)

    // when
    val result =
      shouldThrow<ServiceException> {
        server.registerInvitation(user.id)
      }.errorCode

    // then
    result shouldBe ServerErrorCode.ALREADY_REGISTERED_USER
  }

  it("hasRole 테스트") {
    // given
    val ownerId = "owner"
    val server =
      Server(
        id = "serverId",
        name = "serverName",
        invitedUserIds = mutableSetOf("invitedUserId"),
        authorities = mapOf(ownerId to ServerRole.OWNER),
      )
    val user = ServerUser("invitedUserId", "userName", "profileImageUrl")

    // when
    val hasRole = server.hasRole(ownerId, ServerAuthority.INVITE)
    // then
    hasRole shouldBe true
  }
})
