import { ChatBubble, SupervisedUserCircle } from "@mui/icons-material";
import React from "react";

const Header = () => {
  return (
    <nav className="flex justify-between items-center p-4 min-h-14 bg-black">
      <div>
        <span className="text-white">Dicotown</span>
      </div>
      <div className="flex justify-center items-center gap-2">
        <ChatBubble className="text-white" />
        <SupervisedUserCircle className="text-white" />
      </div>
    </nav>
  );
};

export default Header;
