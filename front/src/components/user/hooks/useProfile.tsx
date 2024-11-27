import { useEffect, useState } from "react";
import { getUserProfileData, UserProfileType } from "../api/user";

const useProfile = (userId: string | null, token: string | null) => {
  const [user, setUser] = useState<UserProfileType | undefined>(undefined);

  const getUserProfile = async () => {
    try {
      const data = await getUserProfileData(userId, token);
      setUser(data);

      return data;
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    getUserProfile();
  }, [userId, token]);

  return user;
};

export default useProfile;
