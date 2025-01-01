package kpring.server.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import kpring.core.server.dto.request.GetServerCondition
import kpring.server.domain.ServerProfile
import kpring.server.domain.ServerRole
import kpring.server.entity.QServerProfileEntity
import kpring.server.entity.ServerEntity
import kpring.server.entity.ServerProfileEntity
import kpring.server.util.testServer
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
class ServerProfileRepositoryTest(
  serverProfileRepository: ServerProfileRepository,
  serverRepository: ServerRepository,
  serverProfileCustomRepository: ServerProfileCustomRepository,
) : DescribeSpec({

    it("특정한 서버 아이디를 가지는 프로파일을 삭제한다.") {
      // given
      val qProfile = QServerProfileEntity.serverProfileEntity
      val serverId = "serverId"

      repeat(3) { index ->
        val serverProfileEntity =
          ServerProfileEntity(
            id = null,
            userId = "userId $index",
            name = "name $index",
            imagePath = "imagePath/$index",
            serverId = serverId,
            role = ServerRole.MEMBER,
            bookmarked = false,
          )
        serverProfileRepository.save(serverProfileEntity)
      }

      // when
      serverProfileRepository.deleteByServerId(serverId)

      // then
      val result =
        serverProfileRepository.findAll(
          qProfile.serverId.eq(serverId),
        )

      result shouldHaveSize 0
    }

    describe("서버 프로필 조회") {

      it("제한된 서버 프로필을 조회하는 조건을 사용한다면 모든 프로필을 조회하지 않고 조건에 해당하는 서버 프로필만을 조회한다.") {
        // given
        val server1 = testServer(name = "server1")
        val server2 = testServer(name = "server2")

        val serverEntity1 = serverRepository.save(ServerEntity(server1))
        val serverEntity2 = serverRepository.save(ServerEntity(server2))

        val server1Profile =
          ServerProfile(
            id = null,
            userId = "testUserId",
            name = "test",
            imagePath = "test",
            role = ServerRole.MEMBER,
            server = serverEntity1.toDomain(),
          )

        serverProfileRepository.save(ServerProfileEntity(server1Profile))

        val condition = GetServerCondition(serverIds = listOf(serverEntity1.id!!))

        // when
        val serverProfiles = serverProfileCustomRepository.getProfiles(condition, "testUserId")

        // then
        serverProfiles shouldHaveSize 1
      }
    }
  })
