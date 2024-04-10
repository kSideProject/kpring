package kpring.auth.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.date.shouldBeBefore
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldNotHaveLength
import io.mockk.coEvery
import io.mockk.mockk
import kpring.auth.repository.ExpireTokenRepository
import kpring.core.auth.dto.request.CreateTokenRequest
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

class TokenServiceTest : BehaviorSpec({
    val tokenRepository : ExpireTokenRepository = mockk()
    val tokenService = TokenService(
        accessDuration = 1000, // 1s
        refreshDuration = 10000, // 10s
        secretKey = "testsecretkey-dfasdfasdfasdfasdfasdfsadfasdfasdfasdfasdf",
        tokenRepository
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

    Given("토큰이 주어졌을 때") {
        val tokenInfo = tokenService.createToken(
            CreateTokenRequest(
                id = "test", nickname = "test nick"
            )
        )

        When("토큰을 재생성하면") {

            coEvery { tokenRepository.isExpired(any()) } returns false
            val newTokenInfo = tokenService.reCreateAccessToken(tokenInfo.refreshToken)
            Then("새로운 엑세스 토큰을 반환한다.") {
                newTokenInfo.accessToken shouldNotBe tokenInfo.accessToken
            }
        }

        When("토큰이 유효하지 않다면"){
            then("타입이 Refresh가 아니라면 예외를 발생시킨다.") {
                coEvery { tokenRepository.isExpired(any()) } returns false
                shouldThrow<IllegalArgumentException> {
                    tokenService.reCreateAccessToken(tokenInfo.accessToken)
                }
            }

            then("만료 처리된 토큰이라면 예외를 발생시킨다.") {
                coEvery { tokenRepository.isExpired(any()) } returns true
                shouldThrow<IllegalArgumentException> {
                    tokenService.reCreateAccessToken(tokenInfo.accessToken)
                }
            }

        }
    }
})