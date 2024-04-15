package kpring.user.service

import kpring.user.dto.request.FriendsRequestDto
import kpring.user.dto.result.FriendsResultDto
import kpring.user.entity.User
import kpring.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserAddServiceImpl(
    private val userRepository: UserRepository
) : UserAddService {
    override fun addFriends(friendsRequestDto: FriendsRequestDto): FriendsResultDto {
        var users: List<User> = userRepository.findByIdIn(friendsRequestDto.friendIds)
        users.forEach { user ->
            user.followers.forEach { follower ->
                follower.followees.add(user)
            }
            user.followees.forEach { followee ->
                followee.followers.add(user)
            }
        }
        return FriendsResultDto(friendsRequestDto.friendIds)
    }
}
