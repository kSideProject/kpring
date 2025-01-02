import useFriendsList from "@/hooks/user/useFriendsList";
import React from "react";
import FriendItem from "../molecules/FriendItem";

type FriendsSideBarProps = {
  onAvatarClick: () => void;
};

const FriendsSideBar: React.FC<FriendsSideBarProps> = ({ onAvatarClick }) => {
  const { friends } = useFriendsList();

  return (
    <React.Fragment>
      <ul>
        {friends?.friends.map((friend) => (
          <FriendItem
            key={friend.friendId}
            username={friend.username}
            onAvatarClick={onAvatarClick}
          />
        ))}
      </ul>
    </React.Fragment>
  );
};

export default FriendsSideBar;
