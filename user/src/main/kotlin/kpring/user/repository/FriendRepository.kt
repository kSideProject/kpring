package kpring.user.repository

import kpring.user.entity.Friend
import org.springframework.data.jpa.repository.JpaRepository

interface FriendRepository : JpaRepository<Friend, Long> {
  fun existsByUserIdAndFriendId(
    userId: Long,
    friendId: Long,
  ): Boolean
}
