package kpring.server.application.port.output

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import kpring.server.util.testServer
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
@SpringBootTest
class UpdateServerPortTest(
  val updateServerPort: UpdateServerPort,
  val createServerPort: SaveServerPort,
  val getServerProfilePort: GetServerProfilePort,
  val getServerPort: GetServerPort,
) : DescribeSpec({

    it("유저를 초대가 작동한다.") {
      // given
      val server = createServerPort.create(testServer(id = null))

      // when
      repeat(5) {
        val userId = "test$it"
        server.registerInvitation(userId)
        updateServerPort.inviteUser(server.id!!, userId)
      }

      // then
      val result = getServerPort.get(server.id!!)
      result.invitedUserIds shouldHaveSize server.invitedUserIds.size
    }

    it("가입 유저를 추가할 수 있다.") {
      // given
      val server = createServerPort.create(testServer(id = null, users = mutableSetOf()))
      val userId = "userId"

      server.registerInvitation(userId)
      updateServerPort.inviteUser(server.id!!, userId)
      val profile = server.addUser(userId, "name", "/path")

      // when
      updateServerPort.addUser(profile)

      // then
      val result = getServerPort.get(server.id!!)

      getServerProfilePort.getAll(server.id!!) shouldHaveSize server.users.size
      result.users.size shouldBeEqual server.users.size
      result.users shouldContain userId
      result.invitedUserIds shouldHaveSize server.invitedUserIds.size
    }
  })
