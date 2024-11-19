import axios from "axios";
import { UserFriendsListType } from "../types/friends";

export const getFriendsData = async (
  userId: string | null,
  token: string | null
) => {
  const url = `${process.env.REACT_APP_BASE_URL}/user/api/v1/user/${userId}/friends`;

  try {
    const response = await axios({
      method: "get",
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log(response.data);
    return response.data as UserFriendsListType;
  } catch (error) {
    console.error(error);
  }
};
