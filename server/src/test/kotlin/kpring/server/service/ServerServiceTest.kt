package kpring.server.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.server.domain.ServerProfile
import kpring.server.domain.ServerRole
import kpring.server.repository.ServerCustomRepository
import kpring.server.repository.ServerProfileCustomRepository
import kpring.server.repository.ServerProfileRepository
import kpring.server.repository.ServerRepository
import kpring.server.util.testServer

class ServerServiceTest(
  val serverRepository: ServerRepository = mockk(),
  val serverProfileRepository: ServerProfileRepository = mockk(),
  val serverCustomRepository: ServerCustomRepository = mockk(),
  val serverProfileCustomRepository: ServerProfileCustomRepository = mockk(),
  val service: ServerServiceImpl =
    ServerServiceImpl(
      serverRepository,
      serverProfileRepository,
      serverCustomRepository,
      serverProfileCustomRepository,
    ),
) : DescribeSpec({
    it("유저 초대시 초대하는 유저가 권한이 없다면 예외를 던진다") {
      // given
      val invitorId = "invitorId"
      val server = testServer()
      val userId = server.host.id
      val serverId = server.id!!

      server.invitedUserIds.add(invitorId)

      val serverProfile =
        ServerProfile(
          id = null,
          userId = invitorId,
          name = "invitor",
          imagePath = "imagePath",
          role = ServerRole.MEMBER,
          server = server,
        )

      every { service.get(serverId) } returns server
      every { serverProfileCustomRepository.get(serverId, invitorId) } returns serverProfile

      // when
      val ex =
        shouldThrow<ServiceException> {
          service.inviteUser(serverId, invitorId, userId)
        }

      // then
      ex.errorCode shouldBe CommonErrorCode.FORBIDDEN
    }

    it("삭제하는 서버에 대한 삭제 권한이 없는 유저라면 예외가 발생한다.") {
      // given
      val server = testServer()
      val serverId = server.id!!
      val userId = server.host.id
      val serverProfile =
        ServerProfile(
          id = null,
          userId = userId,
          name = "name",
          imagePath = "/imagePath",
          role = ServerRole.MEMBER,
          server = server,
        )

      every { serverProfileCustomRepository.get(serverId, userId) } returns serverProfile

      // when
      val ex =
        shouldThrow<ServiceException> {
          service.deleteServer(serverId, userId)
        }

      // then
      ex.errorCode shouldBe CommonErrorCode.FORBIDDEN
    }
  })
