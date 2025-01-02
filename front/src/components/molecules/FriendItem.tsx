import Avatar from "boring-avatars";
import React from "react";

type FriendItemProps = {
  username: string;
  onAvatarClick: () => void;
};

const FriendItem: React.FC<FriendItemProps> = ({ username, onAvatarClick }) => {
  return (
    <li className="flex items-center gap-3">
      <Avatar
        name={username}
        variant="beam"
        onClick={onAvatarClick}
        size={40}
      />
      <span>{username}</span>
    </li>
  );
};

export default FriendItem;
