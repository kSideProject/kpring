package kpring.server.repository

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import kpring.core.global.exception.CommonErrorCode
import kpring.core.global.exception.ServiceException
import kpring.core.server.dto.request.UpdateHostAtServerRequest
import kpring.server.domain.ServerRole
import kpring.server.domain.Theme
import kpring.server.entity.ServerEntity
import kpring.server.service.ServerService
import kpring.server.util.testServer
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
class ServerRepositoryTest(
  serverRepository: ServerRepository,
  serverCustomRepository: ServerCustomRepository,
  serverProfileCustomRepository: ServerProfileCustomRepository,
  service: ServerService,
) : DescribeSpec({
    it("유저를 초대가 작동한다.") {
      // given
      val server = serverCustomRepository.create(testServer(id = null))

      // when
      repeat(5) {
        val userId = "test$it"
        server.registerInvitation(userId)
        serverCustomRepository.inviteUser(server.id!!, userId)
      }

      // then
      val result = service.get(server.id!!)
      result.invitedUserIds shouldHaveSize server.invitedUserIds.size
    }

    it("가입 유저를 추가할 수 있다.") {
      // given
      val server = serverCustomRepository.create(testServer(id = null, users = mutableSetOf()))
      val userId = "userId"

      server.registerInvitation(userId)
      serverCustomRepository.inviteUser(server.id!!, userId)
      val profile = server.addUser(userId, "name", "/path")

      // when
      serverCustomRepository.addUser(profile)

      // then
      val result = service.get(server.id!!)

      serverProfileCustomRepository.getAll(server.id!!) shouldHaveSize server.users.size
      result.users.size shouldBeEqual server.users.size
      result.users shouldContain userId
      result.invitedUserIds shouldHaveSize server.invitedUserIds.size
    }

    it("서버 권한을 다른 사용자에게 상속한다.") {
      // given
      val userId = "userId"
      val username = "username"
      val newHostId = "new_host_id"
      val newHostName = "new_host_name"
      val otherUser = UpdateHostAtServerRequest(newHostId, newHostName)
      val server =
        serverCustomRepository.create(
          testServer(
            id = null,
            name = "",
            users = mutableSetOf(userId, newHostId),
            invitedUserIds = mutableSetOf(),
            theme = Theme.default(),
            categories = setOf(),
            hostId = userId,
            hostName = username,
          ),
        )

      server.updateServerHost(newHostId, newHostName)

      // when
      serverCustomRepository.updateServerHost(server.id!!, userId, otherUser)

      // then
      val result = service.get(server.id!!)
      result.host.id shouldBeEqual newHostId
      result.host.name shouldBeEqual newHostName
    }

    it("존재하지 서버 id를 조회하는 경우 에러가 발생한다.") {
      // given
      val notExistServerId = "notExistServerId"

      // when
      val ex =
        shouldThrow<ServiceException> {
          service.get(notExistServerId)
        }

      // then
      ex.errorCode shouldBe CommonErrorCode.NOT_FOUND
    }

    it("저장된 서버의 정보를 조회할 수 있다.") {
      // given
      val domain = testServer()
      val serverEntity = serverRepository.save(ServerEntity(domain))

      // when
      val server = service.get(serverEntity.id!!)

      // then
      server.name shouldBe domain.name
      server.users shouldHaveSize domain.users.size
      server.users shouldContainAll domain.users
    }

    it("존재하지 않은 서버를 조회하면 예외가 발생한다.") {
      // when
      val exception =
        shouldThrow<ServiceException> {
          service.get("not-exist")
        }

      // then
      exception.errorCode shouldBe CommonErrorCode.NOT_FOUND
    }

    it("유저가 속한 서버 목록을 조회할 수 있다.") {
      // given
      val userId = "test-user"
      val server = testServer(id = null, hostId = userId)

      repeat(2) {
        serverRepository.save(ServerEntity(server))
      }

      // when
      val servers = serverCustomRepository.getServerWith(userId)

      // then
      servers shouldHaveSize 2
      servers.forEach {
        it.users shouldHaveSize server.users.size
        it.users shouldContainAll server.users
      }
    }

    it("서버를 저장하면 생성한 유저는 서버의 소유자가 된다.") {
      // given
      val domain = testServer(id = null)
      val userId = domain.host.id

      // when
      val server = serverCustomRepository.create(domain)
      val profile = serverProfileCustomRepository.get(server.id!!, userId)

      // then
      server.host.id shouldBe domain.host.id
      server.host.name shouldBe domain.host.name
      profile.role shouldBe ServerRole.OWNER
    }
  })
