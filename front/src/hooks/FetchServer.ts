import { useQuery } from "@tanstack/react-query";
import axios from "axios";

export interface Servers {
  serverName: string;
  userId: string;
  theme: string;
  categories: string;
}

const fetchServers = async (token: string) => {
  const url = "http://kpring.duckdns.org/server/api/v1/server";

  try {
    const response = await axios({
      method: "get",
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log(response);
    return response;
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
