import React from "react";
import useNewRequestedFriends from "../../hooks/useNewRequestedFriends";
import Avatar from "boring-avatars";

const NewFriendsList = () => {
  const token = localStorage.getItem("dicoTown_AccessToken");
  const data = useNewRequestedFriends("2", token);

  return (
    <div>
      {data?.data.friendRequests.map((newFriend) => (
        <div
          key={newFriend.friendId}
          className="flex justify-between items-center">
          <div className="flex items-center gap-3">
            <Avatar name={newFriend.username} variant="beam" size={44} />
            <span>{newFriend.username}</span>
          </div>
          <div className="flex gap-2">
            <button>수락</button>
            <button>거절</button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default NewFriendsList;
