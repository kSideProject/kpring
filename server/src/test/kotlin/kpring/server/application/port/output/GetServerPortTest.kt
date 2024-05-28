package kpring.server.application.port.output

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.server.adapter.output.mongo.entity.ServerEntity
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
class GetServerPortTest(
  val getServerPort: GetServerPort,
  val serverRepository: ServerRepository,
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

    it("저장된 서버의 정보를 조회할 수 있다.") {
      // given
      val userIds = mutableListOf("id")
      val serverEntity =
        serverRepository.save(
          ServerEntity(name = "test", users = userIds),
        )

      // when
      val server = getServerPort.get(serverEntity.id)

      // then
      server.name shouldBe "test"
      server.users shouldHaveSize 1
      server.users shouldContain "id"
    }

    it("존재하지 않은 서버를 조회하면 예외가 발생한다.") {
      // when
      val exception =
        shouldThrow<ServiceException> {
          getServerPort.get("not-exist")
        }

      // then
      exception.errorCode shouldBe CommonErrorCode.NOT_FOUND
    }

    it("유저가 속한 서버 목록을 조회할 수 있다.") {
      // given
      val userId = "test-user"
      val userIds = mutableListOf(userId)

      repeat(2) {
        serverRepository.save(ServerEntity(name = "test$it", users = userIds))
      }

      // when
      val servers = getServerPort.getServerWith(userId)

      // then
      servers shouldHaveSize 2
      servers.forEach {
        it.users shouldHaveSize 1
        it.users shouldContain userId
      }
    }
  })
