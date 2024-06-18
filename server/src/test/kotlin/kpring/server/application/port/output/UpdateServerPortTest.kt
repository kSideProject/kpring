package kpring.server.application.port.output

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import kpring.server.domain.Server
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
      val userId = "userId"
      val domain = Server(name = "serverName", users = mutableSetOf(userId))
      val server = createServerPort.create(domain)

      // when
      repeat(5) {
        updateServerPort.inviteUser(server.id!!, "test$it")
      }

      // then
      val result = getServerPort.get(server.id!!)
      result.invitedUserIds shouldHaveSize 5
    }

    it("가입 유저를 추가할 수 있다.") {
      // given
      val ownerId = "ownerId"
      val domain = Server(name = "serverName", users = mutableSetOf(ownerId))
      val server = createServerPort.create(domain)
      val userId = "userId"

      server.registerInvitation(userId)
      updateServerPort.inviteUser(server.id!!, userId)
      val profile = server.addUser(userId, "name", "/path")

      // when
      updateServerPort.addUser(profile)

      // then
      val result = getServerPort.get(server.id!!)
      result.users shouldContain userId
      result.invitedUserIds shouldHaveSize 0
    }
  })
