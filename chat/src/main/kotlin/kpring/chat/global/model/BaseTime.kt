package kpring.chat.global.model

import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class BaseTime {

    lateinit var createdAt: LocalDateTime

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    init {
        val now: LocalDateTime = LocalDateTime.now()
        createdAt = now
        updatedAt = now
    }
}