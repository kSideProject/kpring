import Avatar from "boring-avatars";
import React, { useEffect } from "react";
import { RiEditCircleFill } from "react-icons/ri";
import Text from "../atoms/Text";
import Button from "../atoms/Button";
import Divider from "../atoms/Divider";
import { useLocation, useNavigate } from "react-router";
import { HiUserRemove } from "react-icons/hi";
import { deleteFriend } from "@/api/user";
import { useLoginStore } from "@/store/useLoginStore";
import Cookies from "js-cookie";
import useModalStore from "@/store/useModalStore";

type UserProfileProps = {
  nickname: string | undefined;
  isCurrentUser: boolean;
  email?: string | undefined;
  selectedUserId: string | undefined;
  onLogout?: () => void;
  onSendDM?: () => void;
};

const UserProfile: React.FC<UserProfileProps> = ({
  nickname,
  email,
  selectedUserId,
  isCurrentUser,
  onLogout,
  onSendDM,
}) => {
  const navigate = useNavigate();
  const location = useLocation();
  const { closeModal } = useModalStore();
  const userId = Cookies.get("userId");
  const { accessToken } = useLoginStore();

  const handleNavigate = () => {
    navigate("/profile");
  };

  const handleOnDeleteFriend = async () => {
    try {
      if (userId && selectedUserId) {
        await deleteFriend(userId, selectedUserId, accessToken);

        console.log(userId, selectedUserId, accessToken);
      }
      console.log("친구 삭제 성공");
    } catch (error) {
      throw new Error("친구 삭제에 실패했습니다.");
    }
  };

  useEffect(() => {
    if (location.pathname === "/profile") {
      closeModal();
    }
  }, [location.pathname, closeModal]);

  return (
    <div className="flex flex-col gap-4">
      <div className="flex justify-between items-center">
        <div className="flex items-center">
          <Avatar name={nickname} size={48} variant="beam" />
          <div className="flex flex-col ml-2">
            <Text styles="text-p font-bold text-black">{nickname}</Text>
            <Text styles="text-small font-bold text-black">{email}</Text>
          </div>
        </div>

        <div>
          {isCurrentUser ? (
            <Button
              onClick={handleNavigate}
              icon={<RiEditCircleFill />}
              style={`bg-quaternary text-primary transition duration-300 hover:bg-primary hover:text-white`}>
              프로필 수정
            </Button>
          ) : (
            <Button
              onClick={handleOnDeleteFriend}
              icon={<HiUserRemove />}
              style={`bg-error text-black transition duration-300 hover:bg-error hover:text-white`}>
              친구삭제
            </Button>
          )}
        </div>
      </div>

      <Divider style={`bg-lightGray`} />

      <div>
        {isCurrentUser ? (
          <>
            <Button onClick={() => onLogout} style={`bg-primary text-white`}>
              로그아웃
            </Button>
          </>
        ) : (
          <Button onClick={() => onSendDM} style={`bg-primary text-white`}>
            메세지보내기
          </Button>
        )}
      </div>
    </div>
  );
};

export default UserProfile;
