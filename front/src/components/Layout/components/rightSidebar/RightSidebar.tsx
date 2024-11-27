import React from "react";
import { IoCloseCircle } from "react-icons/io5";
import FriendsSidebar from "./components/FriendsSidebar";
import MessageSidebar from "./components/MessageSidebar";

const RightSidebar = ({
  activeSidebar,
  onClose,
}: {
  activeSidebar: "friends" | "messages" | null;
  onClose: () => void;
}) => {
  return (
    <div
      className={`fixed top-0 right-0 h-full w-80 bg-gray-800 p-4 text-white transform transition-transform duration-300 ${
        activeSidebar ? "translate-x-0" : "translate-x-full"
      } shadow-lg`}>
      <div className="flex justify-between items-center mb-5 max-h-12">
        {activeSidebar === "friends" ? <p>Friends</p> : <p>Massages</p>}
        <IoCloseCircle
          onClick={onClose}
          className="text-3xl text-white hover:text-red-500"
        />
      </div>
      {activeSidebar === "friends" && <FriendsSidebar />}
      {activeSidebar === "messages" && <MessageSidebar />}
    </div>
  );
};

export default RightSidebar;
