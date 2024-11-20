import { useEffect, useState } from "react";
import { GetRequestedFriendsResponseType } from "../types/friends";
import { getRequestedFriendsData } from "../api/friends";

const useNewRequestedFriends = (
  userId: string | null,
  token: string | null
) => {
  const [newFriends, setNewFriends] = useState<
    GetRequestedFriendsResponseType | undefined
  >(undefined);

  const getNewRequetedFriends = async () => {
    try {
      const response = await getRequestedFriendsData(userId, token);
      if (response) {
        setNewFriends(response);
      }
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (token && userId) getNewRequetedFriends();
  }, [userId, token]);

  return newFriends;
};

export default useNewRequestedFriends;
