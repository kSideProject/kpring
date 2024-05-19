package kpring.server.adapter.output.mongo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.server.adapter.output.mongo.entity.ServerEntity
import kpring.server.adapter.output.mongo.entity.ServerUserEntity
import kpring.server.adapter.output.mongo.repository.ServerRepository
import kpring.server.domain.ServerUser
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(
  initializers = [SpringTestContext.SpringDataMongo::class],
)
class GetServerPortMongoImplTest(
  val getServerPortMongoImpl: GetServerPortMongoImpl,
  val serverRepository: ServerRepository,
) : DescribeSpec({
    it("저장된 서버의 정보를 조회할 수 있다.") {
      // given
      val serverUserEntities =
        mutableListOf(
          ServerUserEntity("id", "name", "/path"),
        )
      val serverEntity =
        serverRepository.save(
          ServerEntity(name = "test", users = serverUserEntities),
        )

      // when
      val server = getServerPortMongoImpl.get(serverEntity.id)

      // then
      server.name shouldBe "test"
      server.users shouldHaveSize 1
      server.users shouldContain ServerUser("id", "name", "/path")
    }

    it("존재하지 않은 서버를 조회하면 예외가 발생한다.") {
      // when
      val exception =
        shouldThrow<ServiceException> {
          getServerPortMongoImpl.get("not-exist")
        }

      // then
      exception.errorCode shouldBe CommonErrorCode.NOT_FOUND
    }
  })
