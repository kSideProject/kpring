import { useEffect, useState } from "react";
import { UserFriendsListType } from "../types/friends";
import { getFriendsData } from "../api/friends";

const useFriendsList = (userId: string | null, token: string | null) => {
  const [friends, setFriends] = useState<UserFriendsListType | undefined>(
    undefined
  );

  const getFriends = async () => {
    try {
      const data = await getFriendsData(userId, token);
      setFriends(data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    getFriends();
  }, [userId, token]);
};

export default useFriendsList;
