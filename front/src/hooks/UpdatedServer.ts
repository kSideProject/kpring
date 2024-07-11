import { useMutation } from "@tanstack/react-query";
import axios from "axios";
import { ServerType } from "../types/server";

const createServers = async (data: ServerType, token: string) => {
  const url = "https://kpring.duckdns.org/server/api/v1/server";

  console.log(data);

  try {
    const response = await axios({
      method: "post",
      url,
      data: {
        serverName: data.serverName,
        userId: data.userId,
        theme: null, // 값이 안들어가요...
        categories: null, // 값이 안들어가요...
      },

      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log(response);
    return response;
  } catch (err) {
    console.log(err);
  }
};

const useUpdatedServers = (token: string | null) => {
  return useMutation({
    mutationFn: (data: ServerType) => {
      if (token === null) {
        return Promise.reject(new Error("Token is null"));
      }
      return createServers(data, token);
    },
  });
};

export default useUpdatedServers;
