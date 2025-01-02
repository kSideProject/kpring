import axios from "axios";
import axiosInstance from "./axios/axiosInstance";
import * as UserTypes from "../types/user";
import { USER_API } from "./axios/requests";

// 회원가입
export const join = async (
  email: string,
  password: string,
  username: string
) => {
  try {
    const response = await axiosInstance({
      ...USER_API.POST_REQUEST.join,
      data: {
        email,
        password,
        username,
      },
    });
    console.log("회원가입 성공:", response.data);
  } catch (error) {
    if (axios.isAxiosError(error) && error.response) {
      let errorMessage = "회원가입 과정에서 문제가 발생했습니다.";
      if (error.response.data.message === "Email already exists") {
        errorMessage = "이미 존재하는 이메일입니다. 다시 시도해주세요.";
      } else {
        errorMessage = error.response.data.message || errorMessage;
      }
    }
  }
};

// 로그인
export const login = async (email: string, password: string) => {
  try {
    const response = await axiosInstance({
      ...USER_API.POST_REQUEST.login,
      data: {
        email,
        password,
      },
    });

    const data = response.data;
    if (!data.data.accessToken || !data.data.refreshToken) {
      throw new Error("토큰오류발생! 로그인박스");
    }
    console.log("로그인 성공:", data);
    return data.data;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      if (error.response) {
        // 서버 응답이 있지만, 응답 코드가 2xx가 아님
        console.error("로그인 실패:", error.response.data);
      } else if (error.request) {
        // 요청이 이루어졌으나 응답을 받지 못함
        console.error("응답 없음:", error.request);
      } else {
        // 요청 설정 중에 문제 발생
        console.error("API 호출 중 오류 발생:", error.message);
      }
    } else {
      // axios 에러가 아닌 경우
      console.error("예상치 못한 오류 발생:", error);
    }
    return null;
  }
};

// 회원 검색
export const searchUser = async (
  searchText: string,
  token: string
): Promise<UserTypes.SearchUserResultsResponse | undefined> => {
  try {
    if (searchText) {
      const response = await axiosInstance({
        ...USER_API.GET_REQUEST.fetchUsers,
        params: {
          search: searchText,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    }
  } catch (error) {
    console.log(error);
  }
};

// 사용자 프로필 조회
export const getUserProfile = async (
  userId: string,
  token: string
): Promise<UserTypes.UserProfileResponse | undefined> => {
  try {
    if (userId) {
      const response = await axiosInstance({
        ...USER_API.GET_REQUEST.fetchProfile(userId),
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    }
  } catch (error) {
    throw new Error("유저의 프로필 정보를 가져올 수 없습니다.");
  }
};

// 친구 목록 조회
export const getFriends = async (
  userId: string,
  token: string
): Promise<UserTypes.FriendsListResponse | undefined> => {
  try {
    if (userId) {
      const response = await axiosInstance({
        ...USER_API.GET_REQUEST.fetchFriends(userId),
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    }
  } catch (error) {
    throw new Error("");
  }
};

// 친구 신청
export const requestFriend = async (
  userId: string,
  friendId: string,
  token: string | null
): Promise<UserTypes.RequestFriendResponse | undefined> => {
  try {
    const response = await axiosInstance({
      ...USER_API.POST_REQUEST.requestFriend(userId, friendId),
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

// 친구 신청 조회
export const getFriendRequests = async (
  userId: string,
  token: string
): Promise<UserTypes.FriendRequestsResponseType | undefined> => {
  try {
    const response = await axiosInstance({
      ...USER_API.GET_REQUEST.fetchFriendRequests(userId),
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

// 친구 삭제
export const deleteFriend = async (
  userId: string,
  friendId: string,
  token: string
) => {
  try {
    const response = await axiosInstance({
      ...USER_API.DELETE_REQUEST.deleteFriend(userId, friendId),
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log(response.data);
  } catch (error) {
    console.log(error);
  }
};
