package kpring.chat.global.model

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseTimeWithUpdateAndDelete : BaseTime() {
    @LastModifiedDate
    var updatedAt: LocalDateTime = LocalDateTime.now()
    var deletedAt: LocalDateTime? = null
}