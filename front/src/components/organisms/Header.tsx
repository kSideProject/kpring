import React, { useState } from "react";
import { BsChatQuoteFill } from "react-icons/bs";
import { RiGroup2Fill } from "react-icons/ri";
import RightSideBar from "./RightSideBar";
import { IoPersonAddSharp } from "react-icons/io5";
import Modal from "./Modal";
import AddFriendForm from "../molecules/AddFriendForm";
import { useNavigate } from "react-router";
import useModalStore from "@/store/useModalStore";

const Header: React.FC = () => {
  const navigate = useNavigate();
  const { isOpen, openModal, closeModal, modalType } = useModalStore();
  const [activeSideBar, setActiveSideBar] = useState<
    "friends" | "messages" | null
  >(null);

  const handleOpenFriendsList = () => setActiveSideBar("friends");
  const handleOpenMessageList = () => setActiveSideBar("messages");
  const handleCloseSidebar = () => setActiveSideBar(null);
  const handleAddFriend = () => openModal("addFriend");

  return (
    <nav className="flex justify-between items-center px-5 min-h-14 bg-tertiary">
      <span
        className="text-white font-bold cursor-pointer"
        onClick={() => navigate("/")}>
        Dicotown
      </span>
      <div className="flex justify-center items-center gap-4">
        <BsChatQuoteFill
          className="text-white cursor-pointer transition duration-300 hover:text-secondary"
          fontSize={24}
          onClick={handleOpenMessageList}
        />
        <RiGroup2Fill
          className="text-white cursor-pointer transition duration-300 hover:text-secondary"
          fontSize={24}
          onClick={handleOpenFriendsList}
        />

        <IoPersonAddSharp
          className="text-white cursor-pointer transition duration-300 hover:text-secondary"
          fontSize={20}
          onClick={handleAddFriend}
        />

        <RightSideBar
          activeSideBar={activeSideBar}
          onClose={handleCloseSidebar}
        />
      </div>
      {isOpen && modalType === "addFriend" && (
        <Modal title="친구 추가">
          <AddFriendForm />
        </Modal>
      )}
    </nav>
  );
};

export default Header;
