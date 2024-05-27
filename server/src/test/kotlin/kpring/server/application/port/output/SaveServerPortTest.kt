package kpring.server.application.port.output

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kpring.core.server.dto.request.CreateServerRequest
import kpring.server.domain.ServerRole
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
class SaveServerPortTest(
  val saveServerPort: SaveServerPort,
  val getServerProfilePort: GetServerProfilePort,
) : DescribeSpec({

    it("서버를 저장하면 생성한 유저는 서버의 소유자가 된다.") {
      // given
      val userId = "userId"
      val req = CreateServerRequest(serverName = "serverName")

      // when
      val server = saveServerPort.create(req, userId)
      val profile = getServerProfilePort.get(server.id, userId)

      // then
      profile.role shouldBe ServerRole.OWNER
    }
  })
