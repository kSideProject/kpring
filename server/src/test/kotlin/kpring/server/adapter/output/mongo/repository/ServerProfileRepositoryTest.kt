package kpring.server.adapter.output.mongo.repository

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import kpring.server.adapter.output.mongo.entity.QServerProfileEntity
import kpring.server.adapter.output.mongo.entity.ServerProfileEntity
import kpring.server.domain.ServerRole
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
class ServerProfileRepositoryTest(
  serverProfileRepository: ServerProfileRepository,
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
  })
