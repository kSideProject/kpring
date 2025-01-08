import useRequestedFriends from "@/hooks/user/useRequestedFriends";
import React from "react";
import FriendItem from "../molecules/FriendItem";

const RequestedFriendsList = () => {
  const { requetedFriends } = useRequestedFriends();

  return (
    <ul>
      {requetedFriends?.friendRequests.length === 0 ? (
        <span className="flex justify-center font-bold text-black">
          새로운 친구 요청이 없습니다.
        </span>
      ) : (
        requetedFriends?.friendRequests.map((request) => (
          <FriendItem
            key={request.friendId}
            username={request.username}
            userId={request.friendId}
            selectecUserId={request.friendId}
            onAvatarClick={() => console.log("clicked")}
          />
        ))
      )}
    </ul>
  );
};

export default RequestedFriendsList;
