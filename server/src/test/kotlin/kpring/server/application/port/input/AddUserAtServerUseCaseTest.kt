package kpring.server.application.port.input

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.server.application.port.output.DeleteServerPort
import kpring.server.application.port.output.GetServerPort
import kpring.server.application.port.output.GetServerProfilePort
import kpring.server.application.port.output.UpdateServerPort
import kpring.server.application.service.ServerService
import kpring.server.domain.Server
import kpring.server.domain.ServerProfile
import kpring.server.domain.ServerRole

class AddUserAtServerUseCaseTest(
  val getServerPort: GetServerPort = mockk(),
  val getServerProfilePort: GetServerProfilePort = mockk(),
  val updateServerPort: UpdateServerPort = mockk(),
  val deleteServerPort: DeleteServerPort = mockk(),
  val service: ServerService = ServerService(mockk(), getServerPort, getServerProfilePort, updateServerPort, deleteServerPort),
) : DescribeSpec({

    it("유저 초대시 초대하는 유저가 권한이 없다면 예외를 던진다") {
      // given
      val invitorId = "invitorId"
      val userId = "userId"
      val userName = "hostName"
      val serverId = "serverId"
      val server = Server(name = "serverName", hostName = userName)
      val serverProfile =
        ServerProfile(
          id = null,
          userId = invitorId,
          name = "invitor",
          imagePath = "imagePath",
          role = ServerRole.MEMBER,
          server = server,
        )

      every { getServerPort.get(serverId) } returns server
      every { getServerProfilePort.get(serverId, invitorId) } returns serverProfile

      // when
      val ex =
        shouldThrow<ServiceException> {
          service.inviteUser(serverId, invitorId, userId)
        }

      // then
      ex.errorCode shouldBe CommonErrorCode.FORBIDDEN
    }
  })
