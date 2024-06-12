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
import kpring.server.domain.ServerProfile
import kpring.server.domain.ServerRole
import kpring.server.util.testServer

class DeleteServerUseCaseTest(
  val getServerPort: GetServerPort = mockk(),
  val getServerProfilePort: GetServerProfilePort = mockk(),
  val updateServerPort: UpdateServerPort = mockk(),
  val deleteServerPort: DeleteServerPort = mockk(),
  val service: ServerService = ServerService(mockk(), getServerPort, getServerProfilePort, updateServerPort, deleteServerPort),
) : DescribeSpec({
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

      every { getServerProfilePort.get(serverId, userId) } returns serverProfile

      // when
      val ex =
        shouldThrow<ServiceException> {
          service.deleteServer(serverId, userId)
        }

      // then
      ex.errorCode shouldBe CommonErrorCode.FORBIDDEN
    }
  })
