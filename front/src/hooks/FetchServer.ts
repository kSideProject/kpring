import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import { FetchedServerType } from "../types/server";

// 서버데이터 가져오기
const fetchServers = async (token: string) => {
  const url = "https://kpring.duckdns.org/server/api/v1/server";

  try {
    const response = await axios({
      method: "get",
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data as FetchedServerType;
  } catch (error) {
    console.log(error);
  }
};

const useFetchServers = (token: string | null) => {
  return useQuery({
    queryKey: ["servers"],
    queryFn: () => {
      if (token === null) {
        return Promise.reject(new Error("Token is null"));
      }
      return fetchServers(token);
    },
  });
};

export default useFetchServers;
