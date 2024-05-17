package kpring.user.dto.request

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import jakarta.validation.Validation

class CreateUserRequestTest : StringSpec({

  val validator = Validation.buildDefaultValidatorFactory().validator

  "CreateUserRequestTest 생성 성공" {
    val request = CreateUserRequest(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME)

    val violation = validator.validate(request)
    violation.isEmpty() shouldBe true
  }

  "잘못된 이메일 형식으로 인해 CreateUserRequestTest 생성 실패" {
    val request =
      CreateUserRequest(
        "wrongEmail",
        TEST_PASSWORD,
        TEST_USERNAME,
      )

    val violations = validator.validate(request)
    violations.forEach { violation ->
      violation.message shouldBe "invalid email"
    }
  }

  "잘못된 비밀번호의 형태로 인해 CreateUserRequest 생성 실패" {
    val request =
      CreateUserRequest(
        TEST_EMAIL,
        "wrongPassword",
        TEST_USERNAME,
      )

    val violations = validator.validate(request)
    violations.forEach { violation ->
      violation.message shouldBe "비밀번호는 최소 8자에서 15자 사이, " +
        "대문자와 소문자, 숫자가 포함되어야 하며, " +
        "특수문자 (!, @, #, $)도 사용할 수 있습니다."
    }
  }

  "잘못된 닉네임의 형태로 인해 CreateUserRequest 생성 실패" {
    val request =
      CreateUserRequest(
        TEST_EMAIL,
        TEST_PASSWORD,
        "wrongNickname!",
      )

    val violations = validator.validate(request)
    violations.forEach { violation ->
      violation.message shouldBe "닉네임은 영문 대소문자, 숫자, 한글로 구성되어야 하며, " +
        "1자 이상 32자 이하여야 합니다."
    }
  }
}) {
  companion object {
    private const val TEST_EMAIL = "test@email.com"
    private const val TEST_PASSWORD = "Password123!"
    private const val TEST_USERNAME = "test"
  }
}
