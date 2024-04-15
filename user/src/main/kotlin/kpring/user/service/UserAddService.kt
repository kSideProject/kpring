package kpring.user.service

import kpring.user.dto.request.FriendsRequestDto
import kpring.user.dto.result.FriendsResultDto

interface UserAddService {
    fun addFriends(friendsRequestDto: FriendsRequestDto): FriendsResultDto
}
