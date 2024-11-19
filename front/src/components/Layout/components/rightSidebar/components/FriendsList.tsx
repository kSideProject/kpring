import React from "react";
import useFriendsList from "../hooks/useFriendsList";
import { HiUserAdd } from "react-icons/hi";

const FriendsList: React.FC = () => {
  const token = localStorage.getItem("dicoTown_AccessToken");
  const data = useFriendsList("4", token);

  return (
    <div>
      <div>
        <HiUserAdd />
      </div>
    </div>
  );
};

export default FriendsList;
