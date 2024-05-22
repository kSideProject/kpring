package kpring.chat.example

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kpring.chat.chat.model.Chat
import kpring.chat.chat.model.QChat
import kpring.chat.chat.repository.RoomChatRepository
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

/**
 * querydsl mongoDB와 spring data mongo를 적용하는 예시 코드
 */
@SpringBootTest
@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
class SampleTest(
  val roomChatRepository: RoomChatRepository,
) : DescribeSpec({

    beforeTest {
      roomChatRepository.deleteAll()
    }

    it("query dsl 적용 테스트") {
      // given
      val chat = QChat.chat
      repeat(5) { idx ->
        roomChatRepository.save(
          Chat("testUserId", "testRoomId", "testContent$idx"),
        )
      }

      // when
      val result =
        roomChatRepository.findAll(
          chat.userId.eq("testUserId"),
          chat.userId.asc(),
        )

      // then
      result shouldHaveSize 5
      result.forEach {
        it.userId shouldBe "testUserId"
        println("${it.id} : ${it.content}")
      }
    }

    it("query dsl 적용 테스트 : 다중 조건") {
      // given
      val chat = QChat.chat
      roomChatRepository.deleteAll()
      repeat(5) { idx ->
        roomChatRepository.save(
          Chat("testUserId", "testRoomId", "testContent$idx"),
        )
      }

      // when
      val result =
        roomChatRepository.findAll(
          chat.userId.eq("testUserId")
            .and(chat.content.contains("testContent"))
            // null을 적용하면 조건이 적용되지 않는다.
            .and(null),
          chat.content.desc(),
        )

      // then
      result shouldHaveSize 5
      result.forEach {
        it.userId shouldBe "testUserId"
        println("${it.id} : ${it.content}")
      }
    }
  })
