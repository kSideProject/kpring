import Modal from "./Modal";
import UserProfile from "../molecules/UserProfile";
import useUserProfile from "@/hooks/user/useUserProfile";
import useModal from "@/store/useModalStore";
import React from "react";
import SideBarHaeder from "../molecules/SideBarHaeder";
import FriendsSidebar from "./FriendsSidebar";
import MessageSidebar from "../templates/MessageSidebar";
import useFriendsList from "@/hooks/user/useFriendsList";
import useModalStore from "@/store/useModalStore";

type RightSideBarProps = {
  activeSideBar: "friends" | "messages" | null;
  onClose: () => void;
};

const RightSideBar: React.FC<RightSideBarProps> = ({
  activeSideBar,
  onClose,
}) => {
  const [selectedUser, setSelectedUser] = React.useState<{
    friendId: string;
    username: string;
  } | null>(null);
  const { isOpen, closeModal, openModal, modalType } = useModalStore();

  const handleAvatarClick = (friend: {
    friendId: string;
    username: string;
    email: string;
  }) => {
    setSelectedUser(friend);
    openModal("showProfile");
  };

  const deleteFriendHandler = () => {
    // TODO: 로직추가
  };
  const sendMessageHandler = () => {
    // TODO: 로직추가
  };

  return (
    <React.Fragment>
      <div
        className={`fixed top-0 right-0 h-full w-80 bg-quaternary p-4 text-black transform transition-transform duration-300 ${
          activeSideBar ? "translate-x-0" : "translate-x-full"
        } shadow-lg`}>
        <SideBarHaeder activeSideBar={activeSideBar} onClose={onClose} />
        {activeSideBar === "friends" ? (
          <FriendsSidebar onAvatarClick={handleAvatarClick} />
        ) : (
          <MessageSidebar />
        )}
      </div>
      {isOpen && modalType === "showProfile" && (
        <Modal title="유저 프로필">
          <UserProfile
            nickname={selectedUser?.username}
            selectedUserId={selectedUser?.friendId}
            isCurrentUser={false}
            onSendDM={sendMessageHandler}
          />
        </Modal>
      )}
    </React.Fragment>
  );
};

export default RightSideBar;
