package kpring.user.controller

import kpring.user.dto.request.FriendsRequestDto
import kpring.user.dto.result.FriendsResultDto
import kpring.user.service.UserAddService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserAddController(val userAddService: UserAddService) {
    @PostMapping("/friends/add")
    fun addFriends(
//        @AuthenticationPrincipal UserDetailsImpl userDetails,
//        -> Spring Security 의 UserDetails 에 대해 User Entity 를 통해 구현해야함
        @RequestBody friendsRequestDto: FriendsRequestDto,
    ): FriendsResultDto {
        val friendsResultDto: FriendsResultDto = userAddService.addFriends(friendsRequestDto)
        return friendsResultDto
    }
}
