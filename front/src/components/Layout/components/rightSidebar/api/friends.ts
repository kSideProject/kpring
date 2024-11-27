import axios from "axios";
import {
  GetRequestedFriendsResponseType,
  NewRequestedFriendsListType,
  UserFriendsListType,
} from "../types/friends";

// 현재 유저의 친구 목록 가져오기
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

// 친구 요청하기
export const requestFriend = async (
  userId: string | null,
  friendId: string | null,
  token: string | null
) => {
  const url = `${process.env.REACT_APP_BASE_URL}/user/api/v1/user/${userId}/friend/${friendId}`;

  try {
    const response = await axios({
      method: "post",
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.error(error);
  }
};

// 친구 신청 목록 조회
export const getRequestedFriendsData = async (
  userId: string | null,
  token: string | null
) => {
  const url = `${process.env.REACT_APP_BASE_URL}/user/api/v1/user/${userId}/requests`;

  try {
    const response = await axios({
      method: "get",
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data as GetRequestedFriendsResponseType;
  } catch (error) {
    console.error(error);
  }
};
