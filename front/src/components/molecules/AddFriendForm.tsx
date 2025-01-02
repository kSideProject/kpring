import React, { useState } from "react";
import FormField from "./FormField";
import FriendItem from "./FriendItem";
import { deleteFriend, requestFriend, searchUser } from "@/api/user";
import { useLoginStore } from "@/store/useLoginStore";
import { SearchUsers } from "@/types/user";
import Button from "../atoms/Button";
import useFriendsList from "@/hooks/user/useFriendsList";
import Cookies from "js-cookie";

const AddFriendForm = () => {
  const [searchValue, setSearchValue] = useState("");
  const [searchResults, setSearchResults] = useState<SearchUsers[]>([]);
  const userId = Cookies.get("userId");
  const { friends } = useFriendsList();
  const { accessToken } = useLoginStore();

  const handleSearchFriend = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await searchUser(searchValue, accessToken);
      if (response) {
        setSearchResults(response?.data.users);
      }
    } catch (error) {
      console.error("친구 검색 오류:", error);
    }
  };

  const handleRequestFriend = async (friendId: string) => {
    try {
      if (userId) {
        const response = await requestFriend(userId, friendId, accessToken);
        console.log(response);
      }
    } catch (error) {
      console.error("친구 신청 오류:", error);
    }
  };

  const handleDeleteFriend = async (friendId: string) => {
    try {
      if (userId) {
        const response = await deleteFriend(userId, friendId, accessToken);
        console.log(response);
      }
    } catch (error) {
      console.error("친구 삭제 오류:", error);
    }
  };

  return (
    <div>
      <form onSubmit={handleSearchFriend}>
        <FormField
          value={searchValue}
          name="search friend"
          onChange={(e) => {
            setSearchValue(e.target.value);
          }}
          message=""
        />
      </form>
      <ul>
        {searchResults.length > 0 &&
          searchResults.map((results) => (
            <div
              className="flex items-center justify-between gap-3"
              key={results.userId}>
              <FriendItem
                username={results.username}
                onAvatarClick={() => {}}
              />

              {friends?.friends.some(
                (friend) => friend.friendId === results.userId
              ) ? (
                <Button
                  color="bg-sky-200"
                  onClick={() => handleDeleteFriend(results.userId)}>
                  친구끊기
                </Button>
              ) : (
                <Button
                  color="bg-sky-200"
                  onClick={() => handleRequestFriend(results.userId)}>
                  친구요청
                </Button>
              )}
            </div>
          ))}
      </ul>
    </div>
  );
};

export default AddFriendForm;
