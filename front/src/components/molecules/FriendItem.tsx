import Avatar from "boring-avatars";
import React from "react";

type FriendItemProps = {
  username: string;
  email?: string;
  userId: string;
  selectecUserId: string;
  onAvatarClick: () => void;
};

const FriendItem: React.FC<FriendItemProps> = ({
  username,
  email,
  userId,
  selectecUserId,
  onAvatarClick,
}) => {
  return (
    <li className="flex items-center gap-3">
      <Avatar
        name={username}
        email={email}
        variant="beam"
        onClick={onAvatarClick}
        selectecUserId={selectecUserId}
        size={40}
      />
      <span className="text-p font-bold">{username}</span>
    </li>
  );
};

export default FriendItem;
