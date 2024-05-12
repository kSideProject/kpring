package kpring.server.adapter.output.mongo.entity

import jakarta.persistence.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Entity
@Document(collection = "server")
class ServerEntity(
  name: String? = null
) {
  @Id
  val id: String? = null

  val name: String? = name
}
