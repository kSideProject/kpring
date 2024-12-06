import React from "react";
import useNewRequestedFriends from "../../hooks/useNewRequestedFriends";
import Avatar from "boring-avatars";
import axios from "axios";

const NewFriendsList = () => {
  const token = localStorage.getItem("dicoTown_AccessToken");
  const data = useNewRequestedFriends("2", token);

  const acceptRequestedFriend = async (
    userId: string | null,
    friendId: string | null,
    token: string | null
  ) => {
    const url = `${process.env.REACT_APP_BASE_URL}/user/api/v1/user/${userId}/friend/${friendId}`;

    try {
      const response = await axios({
        method: "patch",
        url,
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      return response.data;
    } catch (error) {
      console.error(error);
    }
  };

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
            <button onClick={() => acceptRequestedFriend("4", "2", token)}>
              수락
            </button>
            <button>거절</button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default NewFriendsList;
