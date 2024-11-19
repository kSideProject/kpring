import React from "react";
import { BsChatQuoteFill } from "react-icons/bs";
import { RiGroup2Fill } from "react-icons/ri";

const Header = () => {
  return (
    <nav className="flex justify-between items-center p-4 min-h-14 bg-black">
      <div>
        <span className="text-white">Dicotown</span>
      </div>
      <div className="flex justify-center items-center gap-3">
        <BsChatQuoteFill className="text-white" fontSize={24} />
        <RiGroup2Fill className="text-white" fontSize={24} />
      </div>
    </nav>
  );
};

export default Header;
