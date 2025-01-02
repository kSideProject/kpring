import Avatar from "boring-avatars";
import React from "react";
import { RiDeleteBin2Fill, RiEditCircleFill } from "react-icons/ri";
import Text from "../atoms/Text";
import Button from "../atoms/Button";

type UserProfileProps = {
  nickname: string | undefined;
  isCurrentUser: boolean;
  onEditProfile?: () => void;
  onLogout?: () => void;
  onDeleteFriend?: () => void;
  onSendDM?: () => void;
};

const UserProfile: React.FC<UserProfileProps> = ({
  nickname,
  isCurrentUser,
  onEditProfile,
  onLogout,
  onDeleteFriend,
  onSendDM,
}) => {
  return (
    <div>
      <div className="flex ">
        <Avatar name={nickname} size={48} variant="beam" />
        <Text color="red" size="xl">
          {nickname}
        </Text>
      </div>

      <div>
        {isCurrentUser ? (
          <RiEditCircleFill onClick={onEditProfile} />
        ) : (
          <RiDeleteBin2Fill onClick={onDeleteFriend} />
        )}
      </div>

      <div>
        {isCurrentUser ? (
          <Button onClick={() => onLogout} color="">
            로그아웃
          </Button>
        ) : (
          <Button onClick={() => onSendDM} color="">
            메세지보내기
          </Button>
        )}
      </div>
    </div>
  );
};

export default UserProfile;
