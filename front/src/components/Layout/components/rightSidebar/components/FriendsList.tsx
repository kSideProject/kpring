import React from "react";
import useFriendsList from "../hooks/useFriendsList";
import { HiUserAdd } from "react-icons/hi";
import TextInput from "../../../../common/input/TextInput";

const FriendsList: React.FC = () => {
  const token = localStorage.getItem("dicoTown_AccessToken");
  const data = useFriendsList("4", token);

  const onChageSearchFriends = () => {};

  return (
    <div>
      <div className="flex justify-between items-center gap-3">
        <TextInput
          placeholder="친구검색"
          value=""
          type="text"
          onChange={onChageSearchFriends}
        />
        <HiUserAdd fontSize={25} />
      </div>
    </div>
  );
};

export default FriendsList;
