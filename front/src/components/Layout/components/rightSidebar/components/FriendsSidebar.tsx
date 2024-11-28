import React from "react";
import useFriendsList from "../hooks/useFriendsList";
import SearchFriendsSection from "./friends/SearchFriendsSection";

const FriendsSidebar: React.FC = () => {
  const token = localStorage.getItem("dicoTown_AccessToken");
  const data = useFriendsList("4", token);

  return (
    <div>
      <div className="flex justify-between items-center gap-3">
        <SearchFriendsSection />
      </div>
    </div>
  );
};

export default FriendsSidebar;
