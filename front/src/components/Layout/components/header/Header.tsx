import React, { useState } from "react";
import { BsChatQuoteFill } from "react-icons/bs";
import { RiGroup2Fill } from "react-icons/ri";
import RightSidebar from "../rightSidebar/RightSidebar";

const Header: React.FC = () => {
  const [activeSidebar, setActiveSidebar] = useState<
    "friends" | "messages" | null
  >(null);

  const handleOpenFriendsList = () => setActiveSidebar("friends");
  const handleOpenMessageList = () => setActiveSidebar("messages");
  const handleCloseSidebar = () => setActiveSidebar(null);

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
        <RightSidebar
          activeSidebar={activeSidebar}
          onClose={handleCloseSidebar}
        />
      </div>
    </nav>
  );
};

export default Header;
