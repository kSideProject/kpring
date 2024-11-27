import React from "react";
import { HiUserAdd } from "react-icons/hi";
import TextInput from "../../../../../common/input/TextInput";

const SearchFriendsSection = () => {
  const onChageSearchFriends = () => {};

  return (
    <React.Fragment>
      <TextInput
        placeholder="친구검색"
        value=""
        type="text"
        onChange={onChageSearchFriends}
      />
      <HiUserAdd fontSize={25} />
    </React.Fragment>
  );
};

export default SearchFriendsSection;
