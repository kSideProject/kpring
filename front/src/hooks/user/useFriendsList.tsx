import { getFriends } from "@/api/user";
import { useLoginStore } from "@/store/useLoginStore";
import { FriendsList } from "@/types/user";
import Cookies from "js-cookie";
import { useEffect, useState } from "react";

const useFriendsList = () => {
  const [friends, setFriends] = useState<FriendsList | undefined>(undefined);
  const userId = Cookies.get("userId");
  const { accessToken } = useLoginStore();

  useEffect(() => {
    const fetchFriends = async () => {
      try {
        if (accessToken && userId) {
          const response = await getFriends(userId, accessToken);
          setFriends(response?.data);
        }
      } catch (error) {
        console.error(error);
      }
    };

    fetchFriends();
  }, [userId, accessToken]);

  return { friends };
};

export default useFriendsList;
