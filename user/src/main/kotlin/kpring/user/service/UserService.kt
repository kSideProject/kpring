package kpring.user.service

import kpring.user.dto.request.CreateUserRequest
import kpring.user.dto.request.UpdateUserProfileRequest
import kpring.user.dto.result.CreateUserResponse
import kpring.user.dto.result.GetUserProfileResponse
import kpring.user.dto.result.UpdateUserProfileResponse

interface UserService {
    fun getProfile(userId: Long): GetUserProfileResponse
    fun updateProfile(userId: Long, request: UpdateUserProfileRequest): UpdateUserProfileResponse
    fun exitUser(userId: Long): Boolean
    fun createUser(request: CreateUserRequest): CreateUserResponse
}
