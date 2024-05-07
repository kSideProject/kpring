package kpring.chat.global.model

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseTime {
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    init {
        createdAt = LocalDateTime.now()
    }
}