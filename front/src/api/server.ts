import * as ServerTypes from "../types/server";
import axiosInstance from "./axios/axiosInstance";
import { SERVER_API } from "./axios/requests";

// 서버 카테고리 조회
export const getCategories =
  async (): Promise<ServerTypes.CategoriesResponseType | null> => {
    try {
      const response = await axiosInstance(
        SERVER_API.GET_REQUEST.fetchServerCategories
      );

      return response.data;
    } catch (error) {
      throw new Error("");
    }
  };

// 서버 조회
export const getServers = async (
  token: string | null
): Promise<ServerTypes.ServerResponseType | undefined> => {
  try {
    const response = await axiosInstance({
      ...SERVER_API.GET_REQUEST.fetchServers,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.error(error);
  }
};

// 서버 삭제
export const deleteServer = async (serverId: string, token: string) => {
  try {
    await axiosInstance({
      ...SERVER_API.DELETE_REQUEST.deleteServer(serverId),
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
  } catch (error) {
    throw new Error("삭제실패");
  }
};

// 서버 생성
export const createServer = async (
  token: string,
  payload: ServerTypes.CreateServerRequest
): Promise<ServerTypes.CreateServerResponse> => {
  if (!token) {
    throw new Error("토큰이 없습니다.");
  }
  try {
    const response = await axiosInstance({
      ...SERVER_API.POST_REQUEST.createServer,
      data: payload,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    throw new Error("");
  }
};
