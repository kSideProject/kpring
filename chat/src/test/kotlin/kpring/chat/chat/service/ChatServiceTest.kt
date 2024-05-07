package kpring.chat.chat.service

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kpring.chat.chat.model.Chat
import kpring.chat.chat.repository.ChatRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest

@SpringBootTest
class ChatServiceTest(
    val chatRepository: ChatRepository,
) : DescribeSpec({

    it("채팅 조회 기능 성공 테스트") {
        // given
        val userId = "testUserId"
        val pageable = PageRequest.of(0, 10)
        repeat(11) { idx ->
            chatRepository.save(
                Chat(
                    "testUserId",
                    "roomId",
                    "nickname",
                    "content${idx}"
                )
            )
        }

        // when
        val chatList = chatRepository.findByUserId(userId, pageable)

        // then
        chatList.size shouldBe 10
        chatList.forEach {
            it.createdAt shouldNotBe null
        }
    }
})
