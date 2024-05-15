package kpring.server

import kpring.test.testcontainer.SpringTestContext
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@ContextConfiguration(initializers = [SpringTestContext.SpringDataMongo::class])
class ServerApplicationTests {
  @Test
  fun contextLoads() {
  }
}
