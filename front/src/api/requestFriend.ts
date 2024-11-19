import axiosInstance from "./axiosInstance";

type FriendsType = {
  friendId: number;
  username: string;
  email: string;
  imagePath: string;
};

type RequestFriendType = {
  userId: string;
  friends: FriendsType[];
};

export const requestFriend = async (token: string | null) => {
  const url = `${process.env.REACT_APP_BASE_URL}/user/api/v1/user/4/friend/2`;

  try {
    const response = await axiosInstance({
      method: "post",
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log(response.data);
    return response.data as RequestFriendType;
  } catch (error) {
    console.log(error);
  }
};

export const viewFriendRequest = async (token: string) => {
  const url = `${process.env.REACT_APP_BASE_URL}/user/api/v1/user/2/requests`;
  try {
    const response = await axiosInstance({
      method: "get",
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.log(error);
  }
};
