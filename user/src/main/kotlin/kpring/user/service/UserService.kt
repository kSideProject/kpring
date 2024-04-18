package kpring.user.service

import kpring.user.dto.request.AddFriendRequest
import kpring.user.dto.request.CreateUserRequest
import kpring.user.dto.request.LoginRequest
import kpring.user.dto.request.UpdateUserProfileRequest
import kpring.user.dto.result.*

interface UserService {
    fun getProfile(userId: Long): GetUserProfileResponse
    fun updateProfile(userId: Long, request: UpdateUserProfileRequest): UpdateUserProfileResponse
    fun exitUser(userId: Long): Boolean
    fun createUser(request: CreateUserRequest): CreateUserResponse
}
