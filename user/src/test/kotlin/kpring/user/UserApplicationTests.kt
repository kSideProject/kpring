package kpring.user

import kpring.test.testcontainer.SpringTestContext
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ActiveProfiles("dev")
@ContextConfiguration(initializers = [SpringTestContext.SpringDataJpaMySql::class])
class UserApplicationTests {
  @Test
  fun contextLoads() {
  }
}
