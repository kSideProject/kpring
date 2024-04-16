package kpring.user.service

import io.kotest.core.spec.style.FunSpec
import kpring.user.dto.request.AddFriendRequest
import kpring.user.dto.result.AddFriendResponse
import kpring.user.entity.User
import kpring.user.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.mockito.Mockito.*
import java.util.*

class UserServiceImplTest() : FunSpec({

    val userRepository: UserRepository = mock()
    var userService: UserServiceImpl = UserServiceImpl(userRepository)
    val friendService = FriendService(userRepository)

    test("친구추가_성공") {
        val user = User(id = 1L, username = "user1", followers = mutableSetOf(), followees = mutableSetOf())
        val friend = User(id = 2L, username = "user2", followers = mutableSetOf(), followees = mutableSetOf())

        `when`(userRepository.findById(user.id!!)).thenReturn(Optional.of(user))
        `when`(userRepository.findById(friend.id!!)).thenReturn(Optional.of(friend))

        val result: AddFriendResponse = friendService.addFriend(AddFriendRequest(friend.id!!), user.id)

        assertEquals(friend.id!!, result.friendId)

        // Verify that the followers and followees relationships are updated
        user.followers.forEach { follower ->
            assertEquals(1, follower.followees.size)
            assertEquals(user, follower.followees.first())
        }
        user.followees.forEach { followee ->
            assertEquals(1, followee.followers.size)
            assertEquals(user, followee.followers.first())
        }

        verify(userRepository).findById(user.id!!)
    }

    test("친구추가실패_유저조회불가능케이스") {
        val userId = 2L
        val friendId = 1L

        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())

        val exception = assertThrows(IllegalArgumentException::class.java) {
            friendService.addFriend(AddFriendRequest(friendId), userId)
        }

        assertEquals("User not found", exception.message)

        verify(userRepository).findById(userId)
    }
})
