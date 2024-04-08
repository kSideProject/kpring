package kpring.auth.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.string.shouldNotHaveLength
import kpring.core.auth.dto.request.CreateTokenRequest
import java.time.LocalDateTime

class TokenServiceTest : BehaviorSpec({
    val tokenService = TokenService(
        accessDuration = 1000, // 1s
        refreshDuration = 10000, // 10s
        secretKey = "testsecretkey-dfasdfasdfasdfasdfasdfsadfasdfasdfasdfasdf"
    )

    beforeTest {
        tokenService.init()
    }

    Given("유저의 id와 유저 닉네임이 주어질 때") {
        val request = CreateTokenRequest(
            id = "testUserId",
            nickname = "test user"
        )

        When("토큰을 생성하면") {
            val response = tokenService.createToken(request)
            then("엑세스 토큰과 리프레시 토큰이 생성된다.") {
                response.apply {
                    accessToken shouldNotHaveLength 0
                    accessExpireAt shouldBeBefore LocalDateTime.now().plusSeconds(1)
                    refreshToken shouldNotHaveLength 0
                    refreshExpireAt shouldBeBefore LocalDateTime.now().plusSeconds(10)
                }
            }
        }
    }
})