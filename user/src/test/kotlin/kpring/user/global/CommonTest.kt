package kpring.user.global

interface CommonTest {
  companion object {
    const val TEST_USER_ID = 1L
    const val TEST_EMAIL = "test@email.com"
    const val TEST_PASSWORD = "Password123!"
    const val TEST_ENCODED_PASSWORD = "EncodedPassword123!"
    const val TEST_USERNAME = "test"
    const val TEST_TOKEN = "Bearer test"

    const val TEST_FRIEND_ID = 2L
  }
}
