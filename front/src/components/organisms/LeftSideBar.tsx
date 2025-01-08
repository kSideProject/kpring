import React from "react";
import AvatarWithAddServer from "../molecules/AvatarWithAddServer";
import Modal from "./Modal";
import useUserProfile from "@/hooks/user/useUserProfile";
import UserProfile from "../molecules/UserProfile";
import CreateServerForm from "./CreateServerForm";
import Divider from "../atoms/Divider";
import ServerList from "../molecules/ServerList";
import useModalStore from "@/store/useModalStore";

const LeftSideBar = () => {
  const { isOpen, openModal, modalType } = useModalStore();
  const { userProfile } = useUserProfile();

  const onLogout = () => {};

  return (
    <React.Fragment>
      <div className="flex flex-col group-hover:items-start p-2 transition-all duration-300">
        <AvatarWithAddServer
          nickname={userProfile?.data.username}
          onAvatarClick={() => openModal("showProfile")}
          onAddServerClick={() => openModal("addServer")}></AvatarWithAddServer>
        <Divider style={`bg-black`} />
        <div className="flex flex-col group-hover:items-start p-2 transition-all duration-300">
          <ServerList />
        </div>
      </div>

      {isOpen && modalType === "showProfile" && (
        <Modal title="유저 프로필">
          <UserProfile
            nickname={userProfile?.data.username}
            email={userProfile?.data.email}
            selectedUserId={userProfile?.data.userId}
            onLogout={onLogout}
            isCurrentUser
            onSendDM={onLogout}
          />
        </Modal>
      )}

      {isOpen && modalType === "addServer" && (
        <Modal title="새로운 서버 생성">
          <CreateServerForm />
        </Modal>
      )}
    </React.Fragment>
  );
};

export default LeftSideBar;
