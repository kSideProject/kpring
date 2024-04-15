package kpring.user.service

import kpring.user.dto.request.FriendsRequestDto
import kpring.user.dto.result.FriendsResultDto
import kpring.user.entity.User
import kpring.user.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.dao.EmptyResultDataAccessException

class UserAddServiceImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var userAddService: UserAddServiceImpl

    @BeforeEach
    fun setUp() {
        userRepository = mock()
        userAddService = UserAddServiceImpl(userRepository)
    }

    @Test
    fun `친구추가_성공`() {
        val friendIds = listOf(1L, 2L)
        val users = listOf(
            User(id = 1L, username = "user1", followers = mutableSetOf(), followees = mutableSetOf()),
            User(id = 2L, username = "user2", followers = mutableSetOf(), followees = mutableSetOf())
        )

        `when`(userRepository.findByIdIn(friendIds)).thenReturn(users)

        val result: FriendsResultDto = userAddService.addFriends(FriendsRequestDto(1L, friendIds))

        assertEquals(friendIds, result.friendIds)

        // Verify that the followers and followees relationships are updated
        users.forEach { user ->
            user.followers.forEach { follower ->
                assertEquals(1, follower.followees.size)
                assertEquals(user, follower.followees.first())
            }
            user.followees.forEach { followee ->
                assertEquals(1, followee.followers.size)
                assertEquals(user, followee.followers.first())
            }
        }

        verify { userRepository.findByIdIn(friendIds) }
    }

    @Test
    fun `친구추가실패_유저조회불가능케이스`() {
        val friendIds = listOf(1L, 2L)

        `when`(userRepository.findByIdIn(friendIds)).thenReturn(emptyList())

        val exception = assertThrows(EmptyResultDataAccessException::class.java) {
            userAddService.addFriends(FriendsRequestDto(1L, friendIds))
        }

        assertEquals("No user found for ids: $friendIds", exception.message)

        verify { userRepository.findByIdIn(friendIds) }
    }
}