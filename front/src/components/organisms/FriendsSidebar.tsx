import useFriendsList from "@/hooks/user/useFriendsList";
import React from "react";
import FriendItem from "../molecules/FriendItem";

type FriendsSideBarProps = {
  onAvatarClick: (friend: {
    friendId: string;
    username: string;
    email: string;
  }) => void;
};

const FriendsSideBar: React.FC<FriendsSideBarProps> = ({ onAvatarClick }) => {
  const { friends } = useFriendsList();

  return (
    <React.Fragment>
      <ul>
        {friends?.friends
          .sort((a, b) => a.username.localeCompare(b.username))
          .map((friend) => (
            <FriendItem
              key={friend.friendId}
              username={friend.username}
              userId={friend.friendId}
              email={friend.email}
              selectecUserId={friend.friendId}
              onAvatarClick={() => onAvatarClick(friend)}
            />
          ))}
      </ul>
    </React.Fragment>
  );
};

export default FriendsSideBar;
