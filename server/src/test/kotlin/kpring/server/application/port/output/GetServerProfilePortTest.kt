package kpring.server.application.port.output

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import kpring.core.server.dto.request.GetServerCondition
import kpring.server.adapter.output.mongo.entity.ServerEntity
import kpring.server.adapter.output.mongo.entity.ServerProfileEntity
import kpring.server.adapter.output.mongo.repository.ServerProfileRepository
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.domain.Server
import kpring.server.domain.ServerProfile
import kpring.server.domain.ServerRole
import kpring.server.domain.Theme
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
class GetServerProfilePortTest(
  val getServerProfilePort: GetServerProfilePort,
  val serverRepository: ServerRepository,
  val serverProfileRepository: ServerProfileRepository,
) : DescribeSpec({

    describe("서버 프로필 조회") {

      it("제한된 서버 프로필을 조회하는 조건을 사용한다면 모든 프로필을 조회하지 않고 조건에 해당하는 서버 프로필만을 조회한다.") {
        // given
        val userIds = mutableSetOf("testUserId")
        val server1 = Server(id = "testId", name = "test", users = userIds, theme = Theme.default(), categories = emptySet())
        val server2 = Server(name = "test", users = userIds)

        val serverEntity1 = serverRepository.save(ServerEntity(server1))
        val serverEntity2 = serverRepository.save(ServerEntity(server2))

        val server1Profile =
          ServerProfile(
            id = null,
            userId = "testUserId",
            name = "test",
            imagePath = "test",
            role = ServerRole.MEMBER,
            server = server1,
          )

        val serverProfileEntity = serverProfileRepository.save(ServerProfileEntity(server1Profile))

        val condition = GetServerCondition(serverIds = listOf(serverEntity1.id!!))

        // when
        val serverProfiles = getServerProfilePort.getProfiles(condition, "testUserId")

        // then
        serverProfiles shouldHaveSize 1
      }
    }
  })
