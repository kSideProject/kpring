package kpring.chat.global.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class BaseTime {

  @CreatedDate
  val createdAt: LocalDateTime = LocalDateTime.now()

  @LastModifiedDate
  var updatedAt: LocalDateTime? = createdAt

}
