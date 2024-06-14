package kpring.chat.global.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@TestConfiguration
@EnableMongoAuditing
class TestMongoConfig {
  @Bean
  fun mongoMappingContext(): MongoMappingContext {
    return MongoMappingContext()
  }
}
