package kpring.server.application.port.input

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.server.application.port.output.GetServerPort
import kpring.server.application.port.output.UpdateServerPort
import kpring.server.application.service.ServerService
import kpring.server.domain.Server

class AddUserAtServerUseCaseTest(
  val updateServerPort: UpdateServerPort = mockk(),
  val getServerPort: GetServerPort = mockk(),
  val service: ServerService = ServerService(mockk(), getServerPort, updateServerPort),
) : DescribeSpec({

  it("유저 초대시 초대하는 유저가 권한이 없다면 예외를 던진다") {
    // given
    val serverId = "serverId"
    val invitorId = "invitorId"
    val userId = "userId"

    every { getServerPort.get(serverId) } returns Server(
      id = serverId,
      name = "serverName",
    )

    // when
    val ex = shouldThrow<ServiceException> {
      service.inviteUser(serverId, invitorId, userId)
    }

    // then
    ex.errorCode shouldBe CommonErrorCode.FORBIDDEN
  }
})
