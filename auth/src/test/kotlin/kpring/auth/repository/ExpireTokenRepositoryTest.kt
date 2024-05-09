package kpring.auth.repository

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.reactor.awaitSingle
import kpring.test.testcontainer.SpringTestContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDateTime

@SpringBootTest
@ContextConfiguration(initializers = [SpringTestContext.SpringDataRedis::class])
class ExpireTokenRepositoryTest(
    val tokenRepository: ExpireTokenRepository,
    val redis: ReactiveStringRedisTemplate,
) : BehaviorSpec({

    Given("토큰이") {
        val tokenId = "testId"
        val endTime = LocalDateTime.now().plusSeconds(10)
        When("만료처리 된다면") {
            tokenRepository.expireToken(tokenId, endTime)
            Then("만료된 토큰 목록에서 조회할 수 있다.") {
                "" shouldBe redis.opsForValue().get(tokenId).awaitSingle()
            }

            Then("만료 여부 조회시 참이어야 한다.") {
                tokenRepository.isExpired(tokenId) shouldBe true
            }
        }
    }
})
