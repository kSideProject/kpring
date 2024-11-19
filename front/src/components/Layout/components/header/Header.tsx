import React, { useState } from "react";
import { BsChatQuoteFill } from "react-icons/bs";
import { RiGroup2Fill } from "react-icons/ri";
import FriendsList from "../rightSidebar/components/FriendsList";

const Header = () => {
  const [openSidebar, setOpenSidebar] = useState(false);

  const handleOpenSidebar = () => {
    setOpenSidebar(true);
  };
  return (
    <nav className="flex justify-between items-center p-4 min-h-14 bg-black">
      <div>
        <span className="text-white">Dicotown</span>
      </div>
      <div className="flex justify-center items-center gap-3">
        <BsChatQuoteFill className="text-white" fontSize={24} />
        <RiGroup2Fill
          className="text-white"
          fontSize={24}
          onClick={handleOpenSidebar}
        />
        {openSidebar && <FriendsList />}
      </div>
    </nav>
  );
};

export default Header;
