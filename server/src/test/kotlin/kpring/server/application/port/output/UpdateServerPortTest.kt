package kpring.server.application.port.output

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import kpring.core.server.dto.request.CreateServerRequest
import kpring.server.domain.ServerUser
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
@SpringBootTest
class UpdateServerPortTest(
  val updateServerPort: UpdateServerPort,
  val createServerPort: SaveServerPort,
  val getServerPort: GetServerPort,
) : DescribeSpec({

    it("유저를 초대가 작동한다.") {
      // given
      val server = createServerPort.create(CreateServerRequest("serverName"))

      // when
      repeat(5) {
        updateServerPort.inviteUser(server.id, "test$it")
      }

      // then
      val result = getServerPort.get(server.id)
      result.invitedUserIds shouldHaveSize 5
    }

    it("가입 유저를 추가할 수 있다.") {
      // given
      val server = createServerPort.create(CreateServerRequest("serverName"))
      val user = ServerUser("test", "test", "test")
      updateServerPort.inviteUser(server.id, user.id)

      // when
      updateServerPort.addUser(server.id, user)

      // then
      val result = getServerPort.get(server.id)
      result.users shouldContain user
      result.invitedUserIds shouldHaveSize 0
    }
  })
