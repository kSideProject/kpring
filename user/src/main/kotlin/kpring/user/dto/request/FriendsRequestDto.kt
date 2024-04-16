package kpring.user.dto.request

data class FriendsRequestDto(
    var userId: Long,
    val friendIds: List<Long>,
)
