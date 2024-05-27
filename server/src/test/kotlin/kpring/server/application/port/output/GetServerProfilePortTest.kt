package kpring.server.application.port.output

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import kpring.core.server.dto.request.GetServerCondition
import kpring.server.adapter.output.mongo.entity.ServerEntity
import kpring.server.adapter.output.mongo.entity.ServerProfileEntity
import kpring.server.adapter.output.mongo.repository.ServerProfileRepository
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.domain.ServerRole
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
        val userIds = mutableListOf("testUserId")

        val serverEntity1 =
          serverRepository.save(
            ServerEntity(name = "test", users = userIds),
          )

        val serverEntity2 =
          serverRepository.save(
            ServerEntity(name = "test", users = userIds),
          )

        val serverProfileEntity =
          serverProfileRepository.save(
            ServerProfileEntity(
              serverId = serverEntity1.id,
              userId = "testUserId",
              name = "test",
              imagePath = "test",
              role = ServerRole.MEMBER,
              bookmarked = false,
            ),
          )

        val condition = GetServerCondition(serverIds = listOf(serverEntity1.id))

        // when
        val serverProfiles = getServerProfilePort.getProfiles(condition, userIds[0])

        // then
        serverProfiles shouldHaveSize 1
      }
    }
  })
