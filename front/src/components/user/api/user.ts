import axios from "axios";

export type UserProfileType = {
  email: string;
  userId: string;
  username: string;
};

// 유저의 프로필 조회하기
export const getUserProfileData = async (
  userId: string | null,
  token: string | null
): Promise<UserProfileType | undefined> => {
  const url = `${process.env.REACT_APP_BASE_URL}/user/api/v1/user/${userId}`;

  try {
    const response = await axios({
      method: "get",
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data as UserProfileType;
  } catch (error) {
    console.error(error);
  }
};
