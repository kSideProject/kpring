import React, { useState } from "react";
import FormField from "./FormField";
import FriendItem from "./FriendItem";
import { deleteFriend, requestFriend, searchUser } from "@/api/user";
import { useLoginStore } from "@/store/useLoginStore";
import { SearchUsers } from "@/types/user";
import useFriendsList from "@/hooks/user/useFriendsList";
import Cookies from "js-cookie";
import { HiUser, HiUserAdd, HiUserRemove } from "react-icons/hi";

const AddFriendForm = () => {
  const [searchValue, setSearchValue] = useState("");
  const [searchResults, setSearchResults] = useState<SearchUsers[]>([]);
  const userId = Cookies.get("userId");
  const { friends } = useFriendsList();
  const { accessToken } = useLoginStore();

  console.log(userId);

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
          placeholder="회원검색"
          style={`text-black font-bold`}
          onChange={(e) => {
            setSearchValue(e.target.value);
          }}
          message=""
        />
      </form>

      <ul className="mt-5">
        {searchResults.length > 0 ? (
          searchResults
            .filter((results) => String(results.userId) !== String(userId))
            .map((result) => {
              const isFriend = friends?.friends.some(
                (friend) => friend.friendId === result.userId
              );

              return (
                <div
                  className="flex items-center justify-between gap-3"
                  key={result.userId}>
                  <FriendItem
                    username={result.username}
                    email={result.email}
                    userId={result.userId}
                    selectecUserId={result.userId}
                    onAvatarClick={() => {}}
                  />

                  {isFriend ? (
                    <HiUserRemove
                      className="bg-red-500 text-white px-3 py-1 rounded"
                      size={40}
                      onClick={() => handleDeleteFriend(result.userId)}
                    />
                  ) : (
                    <HiUserAdd
                      className="bg-primary text-blue-50 px-2 py-1 rounded cursor-pointer"
                      size={40}
                      onClick={() => handleRequestFriend(result.userId)}
                    />
                  )}
                </div>
              );
            })
        ) : (
          <span className="flex justify-center font-bold text-black">
            검색 결과가 없습니다.
          </span>
        )}
      </ul>
    </div>
  );
};

export default AddFriendForm;
