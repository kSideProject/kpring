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
    val server = Server(name = "serverName", hostName = "hostname", hostId = "hostId")
    server.invitedUserIds.add("invitedUserId")

    // when
    val profile = server.addUser("invitedUserId", "userName", "profileImageUrl")

    // then
    server.invitedUserIds shouldNotContain profile.userId
    server.users shouldContain profile.userId
  }

  it("이미 등록된 유저를 초대시 예외가 발생한다.") {
    // given
    val server = Server(name = "serverName", hostName = "hostname", hostId = "hostId", invitedUserIds = mutableSetOf("invitedUserId"))
    val profile = server.addUser("invitedUserId", "userName", "profileImageUrl")

    // when
    val result =
      shouldThrow<ServiceException> {
        server.registerInvitation(profile.userId)
      }.errorCode

    // then
    result shouldBe ServerErrorCode.ALREADY_REGISTERED_USER
  }

  it("테마를 지정하지 않은 서버는 기본 테마를 가진다.") {
    // given & when
    val server = Server(name = "serverName", hostName = "hostname", hostId = "hostId")

    // then
    server.theme shouldBe Theme.default()
  }

  it("카테고리를 지정하지 않은 서버는 빈 카테고리 목록을 가진다.") {
    // given & when
    val server = Server(name = "serverName", hostName = "hostname", hostId = "hostId")

    // then
    server.categories shouldBe emptySet()
  }
})
