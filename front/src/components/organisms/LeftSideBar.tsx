import React from "react";
import useModal from "@/hooks/common/useModal";
import AvatarWithAddServer from "../molecules/AvatarWithAddServer";
import Modal from "./Modal";
import useUserProfile from "@/hooks/user/useUserProfile";
import UserProfile from "../molecules/UserProfile";
import CreateServerForm from "./CreateServerForm";
import Divider from "../atoms/Divider";
import ServerList from "../molecules/ServerList";

const LeftSideBar = () => {
  const { isOpen, openModal, closeModal, modalType } = useModal();
  const { userProfile } = useUserProfile();

  const onLogout = () => {};

  return (
    <React.Fragment>
      <div className="max-w-20 h-screen flex flex-col items-center p-4 bg-slate-300">
        <AvatarWithAddServer
          nickname={userProfile?.data.username}
          onAvatarClick={() => openModal("profile")}
          onAddServerClick={() => openModal("addServer")}></AvatarWithAddServer>
        <Divider />
        <ServerList />
      </div>

      {isOpen && modalType === "profile" && (
        <Modal
          isOpen={isOpen}
          closeModal={() => closeModal(null)}
          title="유저 프로필">
          <UserProfile
            nickname={userProfile?.data.username}
            onLogout={onLogout}
            isCurrentUser
            onEditProfile={onLogout}
            onDeleteFriend={onLogout}
            onSendDM={onLogout}
          />
        </Modal>
      )}

      {isOpen && modalType === "addServer" && (
        <Modal
          isOpen={isOpen}
          closeModal={() => closeModal(null)}
          title="새로운 서버 생성">
          <CreateServerForm />
        </Modal>
      )}
    </React.Fragment>
  );
};

export default LeftSideBar;
