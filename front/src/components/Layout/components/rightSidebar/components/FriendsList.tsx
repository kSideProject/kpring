import React from "react";
import useFriendsList from "../hooks/useFriendsList";

const FriendsList: React.FC = () => {
  const token = localStorage.getItem("dicoTown_AccessToken");

  const data = useFriendsList("4", token);

  return <div></div>;
};

export default FriendsList;
