package kpring.server.application.port.output

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
class GetServerPortTest(
  val getServerPort: GetServerPort,
) : DescribeSpec({
    it("존재하지 서버 id를 조회하는 경우 에러가 발생한다.") {
      // given
      val notExistServerId = "notExistServerId"

      // when
      val ex =
        shouldThrow<ServiceException> {
          getServerPort.get(notExistServerId)
        }

      // then
      ex.errorCode shouldBe CommonErrorCode.NOT_FOUND
    }
  })
