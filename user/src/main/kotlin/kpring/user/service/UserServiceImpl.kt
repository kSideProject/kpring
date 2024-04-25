package kpring.user.service

import kpring.user.dto.request.CreateUserRequest
import kpring.user.dto.request.UpdateUserProfileRequest
import kpring.user.dto.response.CreateUserResponse
import kpring.user.dto.response.GetUserProfileResponse
import kpring.user.dto.response.UpdateUserProfileResponse
import kpring.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {
    override fun getProfile(userId: Long): GetUserProfileResponse {
        TODO("Not yet implemented")
    }

    override fun updateProfile(userId: Long, request: UpdateUserProfileRequest): UpdateUserProfileResponse {
        TODO("Not yet implemented")
    }

    override fun exitUser(userId: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun createUser(request: CreateUserRequest): CreateUserResponse {
        TODO("Not yet implemented")
    }
}
