import { getUserProfile } from "@/api/user";
import { useLoginStore } from "@/store/useLoginStore";
import { UserProfileResponse } from "@/types/user";
import Cookies from "js-cookie";
import { useEffect, useState } from "react";

const useUserProfile = () => {
  const [userProfile, setUserProfile] = useState<UserProfileResponse | null>(
    null
  );
  const { accessToken } = useLoginStore();
  const userId = Cookies.get("userId");

  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        if (userId && accessToken) {
          const res = await getUserProfile(userId, accessToken);
          if (res) {
            setUserProfile(res);
          }
        }
      } catch (err) {
        console.error(err);
      }
    };

    fetchUserProfile();
  }, [userId, accessToken]);

  return { userProfile };
};

export default useUserProfile;
