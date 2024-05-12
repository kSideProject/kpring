package kpring.server.adapter.output.jpa.entity

import jakarta.persistence.*

@Entity
@Table(name = "SERVER")
class ServerEntity(
  name: String? = null
) {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null

  @Column(name = "name", nullable = false)
  val name: String? = name
}
