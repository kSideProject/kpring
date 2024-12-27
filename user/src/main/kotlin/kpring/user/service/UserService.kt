package kpring.user.service

import kpring.core.user.dto.request.CreateUserRequest
import kpring.core.user.dto.request.SearchUserRequest
import kpring.core.user.dto.request.UpdateUserProfileRequest
import kpring.core.user.dto.response.CreateUserResponse
import kpring.core.user.dto.response.GetUserProfileResponse
import kpring.core.user.dto.response.UpdateUserProfileResponse
import kpring.core.user.dto.response.UserSearchResultsResponse
import org.springframework.web.multipart.MultipartFile

interface UserService {
  fun getProfile(userId: Long): GetUserProfileResponse

  fun updateProfile(
    userId: Long,
    request: UpdateUserProfileRequest,
    multipartFile: MultipartFile,
  ): UpdateUserProfileResponse

  fun exitUser(userId: Long): Boolean

  fun createUser(request: CreateUserRequest): CreateUserResponse

  fun searchUsers(searchUserRequest: SearchUserRequest): UserSearchResultsResponse
}
