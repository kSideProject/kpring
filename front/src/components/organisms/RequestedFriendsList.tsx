import useRequestedFriends from "@/hooks/user/useRequestedFriends";
import React from "react";
import FriendItem from "../molecules/FriendItem";

const RequestedFriendsList = () => {
  const { requetedFriends } = useRequestedFriends();
  console.log(requetedFriends);

  return (
    <ul>
      {requetedFriends?.friendRequests.length === 0
        ? "새로운 친구 요청이 없습니다."
        : requetedFriends?.friendRequests.map((request) => (
            <FriendItem
              key={request.friendId}
              username={request.username}
              onAvatarClick={() => console.log("clicked")}
            />
          ))}
    </ul>
  );
};

export default RequestedFriendsList;
