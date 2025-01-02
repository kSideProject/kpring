import { getFriendRequests } from "@/api/user";
import { useLoginStore } from "@/store/useLoginStore";
import { FriendRequests } from "@/types/user";
import Cookies from "js-cookie";
import { useEffect, useState } from "react";

const useRequestedFriends = () => {
  const [requetedFriends, setRequestedFriends] = useState<
    FriendRequests | undefined
  >(undefined);
  const userId = Cookies.get("userId");
  const { accessToken } = useLoginStore();

  useEffect(() => {
    const fetchFriendRequests = async () => {
      if (!userId || !accessToken) return;

      try {
        const response = await getFriendRequests(userId, accessToken);
        setRequestedFriends(response?.data);

        return response?.data;
      } catch (error) {
        console.error(error);
      }
    };

    fetchFriendRequests();
  }, [userId, accessToken]);

  return { requetedFriends };
};

export default useRequestedFriends;
