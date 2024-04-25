package kpring.user.service;

import kpring.user.dto.request.AddFriendRequest;
import kpring.user.dto.result.AddFriendResponse;
import kpring.user.dto.response.DeleteFriendResponse;
import kpring.user.dto.response.GetFriendsResponse;
import kpring.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendService {

    private final UserRepository userRepository;

    public FriendService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public GetFriendsResponse getFriends(Long userId) {
        return null;
    }

    public AddFriendResponse addFriend(AddFriendRequest friendsRequestDto, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.getFollowers().forEach(follower -> {
            follower.getFollowees().add(user);
        });

        user.getFollowees().forEach(follower -> {
            follower.getFollowers().add(user);
        });
        return new AddFriendResponse(friendsRequestDto.getFriendId());
    }

    public DeleteFriendResponse deleteFriend(Long userId, Long friendId) {
        return null;
    }
}
