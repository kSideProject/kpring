package kpring.chat.chat.repository

import kpring.chat.chat.model.Chat
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class RoomChatCustomRepository(
  private val mongoTemplate: MongoTemplate,
) {
  fun findListByContextIdWithPaging(
    contextId: String,
    page: Int,
    size: Int,
  ): List<Chat> {
    val sort: Sort = Sort.by(Sort.Order.desc("createdAt"))
    val pageable: Pageable = PageRequest.of(page, size, sort)
    var query: Query = Query().with(pageable)
    return mongoTemplate.find(query, Chat::class.java)
  }

  fun findPageByContextIdWithPaging(
    contextId: String,
    page: Int,
    size: Int,
  ): Page<Chat> {
    val sort: Sort = Sort.by(Sort.Order.desc("createdAt"))
    val pageable: Pageable = PageRequest.of(page, size, sort)
    val query: Query = Query().with(pageable)
    val list: List<Chat> = mongoTemplate.find(query, Chat::class.java)
    return PageableExecutionUtils.getPage(
      list,
      pageable,
      {
        mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Chat::class.java)
      },
    )
  }
}
