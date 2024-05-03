package kpring.user.dto.request

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation

class CreateUserRequestTest : StringSpec({

    val validator = Validation.buildDefaultValidatorFactory().validator

    "CreateUserRequestTest 생성 성공" {
        val request = CreateUserRequest(TEST_EMAIL, TEST_PASSWORD, TEST_PASSWORD, TEST_USERNAME)

        val violation = validator.validate(request)
        violation.isEmpty() shouldBe true
    }
}) {
    companion object {
        private const val TEST_EMAIL = "test@email.com"
        private const val TEST_PASSWORD = "Password123!"
        private const val TEST_PASSWORD_CHECK = "Password123!"
        private const val TEST_USERNAME = "test"
    }
}