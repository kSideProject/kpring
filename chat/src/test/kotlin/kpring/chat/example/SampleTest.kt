package kpring.chat.example

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kpring.chat.chat.model.Chat
import kpring.chat.chat.model.QChat
import kpring.chat.chat.repository.ChatRepository
import org.springframework.boot.test.context.SpringBootTest

/**
 * querydsl mongoDB와 spring data mongo를 적용하는 예시 코드
 */
@SpringBootTest
class SampleTest(
    val chatRepository: ChatRepository
) : DescribeSpec({

    beforeTest {
        chatRepository.deleteAll()
    }

    it("query dsl 적용 테스트"){
        // given
        repeat(5){ idx ->
            chatRepository.save(
                Chat("testUserId", "testRoomId", "testNickname$idx", "testContent")
            )
        }

        // when
        val result = chatRepository.findAll(
            QChat.chat.userId.eq("testUserId"),
            QChat.chat.userId.asc()
        )

        // then
        result shouldHaveSize 5
        result.forEach {
            it.userId shouldBe "testUserId"
            println("${it.nickname} : ${it.content}")
        }
    }

    it("query dsl 적용 테스트 : 다중 조건") {
        // given
        chatRepository.deleteAll()
        repeat(5){ idx ->
            chatRepository.save(
                Chat("testUserId", "testRoomId", "testNickname$idx", "testContent")
            )
        }

        // when
        val result = chatRepository.findAll(
            QChat.chat.userId.eq("testUserId")
                .and(QChat.chat.nickname.contains("testNickname"))
                .and(null), // null을 적용하면 조건이 적용되지 않는다.
            QChat.chat.nickname.desc()
        )

        // then
        result shouldHaveSize 5
        result.forEach {
            it.userId shouldBe "testUserId"
            println("${it.nickname} : ${it.content}")
        }
    }
})
