import Modal from "./Modal";
import UserProfile from "../molecules/UserProfile";
import useUserProfile from "@/hooks/user/useUserProfile";
import useModal from "@/hooks/common/useModal";
import React from "react";
import SideBarHaeder from "../molecules/SideBarHaeder";
import FriendsSidebar from "./FriendsSidebar";
import MessageSidebar from "../templates/MessageSidebar";

type RightSideBarProps = {
  activeSideBar: "friends" | "messages" | null;
  onClose: () => void;
};

const RightSideBar: React.FC<RightSideBarProps> = ({
  activeSideBar,
  onClose,
}) => {
  const { userProfile } = useUserProfile();
  const { isOpen, closeModal, modalType, openModal } = useModal();

  const deleteFriendHandler = () => {
    // TODO: 로직추가
  };
  const sendMessageHandler = () => {
    // TODO: 로직추가
  };

  return (
    <React.Fragment>
      <div
        className={`fixed top-0 right-0 h-full w-80 bg-gray-800 p-4 text-white transform transition-transform duration-300 ${
          activeSideBar ? "translate-x-0" : "translate-x-full"
        } shadow-lg`}>
        <SideBarHaeder activeSideBar={activeSideBar} onClose={onClose} />
        {activeSideBar === "friends" ? (
          <FriendsSidebar onAvatarClick={() => openModal("profile")} />
        ) : (
          <MessageSidebar />
        )}
      </div>
      {isOpen && modalType === "profile" && (
        <Modal
          isOpen={isOpen}
          closeModal={() => closeModal(null)}
          title="유저 프로필">
          <UserProfile
            nickname={userProfile?.data.username}
            isCurrentUser={false}
            onDeleteFriend={deleteFriendHandler}
            onSendDM={sendMessageHandler}
          />
        </Modal>
      )}
    </React.Fragment>
  );
};

export default RightSideBar;
