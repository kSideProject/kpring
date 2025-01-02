import React, { useState } from "react";
import { BsChatQuoteFill } from "react-icons/bs";
import { RiGroup2Fill } from "react-icons/ri";
import RightSideBar from "./RightSideBar";
import { IoPersonAddSharp } from "react-icons/io5";
import useModal from "@/hooks/common/useModal";
import Modal from "./Modal";
import AddFriendForm from "../molecules/AddFriendForm";

const Header: React.FC = () => {
  const { isOpen, openModal, closeModal } = useModal();
  const [activeSideBar, setActiveSideBar] = useState<
    "friends" | "messages" | null
  >(null);

  const handleOpenFriendsList = () => setActiveSideBar("friends");
  const handleOpenMessageList = () => setActiveSideBar("messages");
  const handleCloseSidebar = () => setActiveSideBar(null);
  const handleAddFriend = () => openModal("addFriend");

  return (
    <nav className="flex justify-between items-center p-4 min-h-14 bg-black">
      <div>
        <span className="text-white">Dicotown</span>
      </div>
      <div className="flex justify-center items-center gap-3">
        <BsChatQuoteFill
          className="text-white"
          fontSize={24}
          onClick={handleOpenMessageList}
        />
        <RiGroup2Fill
          className="text-white"
          fontSize={24}
          onClick={handleOpenFriendsList}
        />

        <IoPersonAddSharp
          className="text-white"
          fontSize={20}
          onClick={handleAddFriend}
        />

        <RightSideBar
          activeSideBar={activeSideBar}
          onClose={handleCloseSidebar}
        />
      </div>
      <Modal
        isOpen={isOpen}
        title="친구 추가"
        closeModal={() => closeModal(null)}>
        <AddFriendForm />
      </Modal>
    </nav>
  );
};

export default Header;
