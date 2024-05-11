package kpring.server.adapter.output.jpa.repository

import kpring.server.adapter.output.jpa.entity.ServerEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ServerRepository : JpaRepository<ServerEntity, Long> {
}
