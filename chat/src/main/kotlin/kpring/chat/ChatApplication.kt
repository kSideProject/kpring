package kpring.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.config.EnableMongoAuditing

@EnableMongoAuditing
@SpringBootApplication
class ChatApplication

fun main(args: Array<String>) {
  runApplication<ChatApplication>(*args)
}
