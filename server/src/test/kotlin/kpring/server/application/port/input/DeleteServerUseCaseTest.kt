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
import kpring.server.application.port.output.UpdateServerPort
import kpring.server.application.service.ServerService
import kpring.server.domain.Server

class DeleteServerUseCaseTest(
  val updateServerPort: UpdateServerPort = mockk(),
  val getServerPort: GetServerPort = mockk(),
  val deleteServerPort: DeleteServerPort = mockk(),
  val service: ServerService = ServerService(mockk(), getServerPort, updateServerPort, deleteServerPort),
) : DescribeSpec({
    it("삭제하는 서버에 대한 삭제 권한이 없는 유저라면 예외가 발생한다.") {
      // given
      val serverId = "serverId"
      val userId = "userId"

      every { getServerPort.get(serverId) } returns
        Server(
          id = serverId,
          name = "serverName",
        )

      // when
      val ex =
        shouldThrow<ServiceException> {
          service.deleteServer(serverId, userId)
        }

      // then
      ex.errorCode shouldBe CommonErrorCode.FORBIDDEN
    }
  })
