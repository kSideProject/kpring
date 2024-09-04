import { useMutation } from "@tanstack/react-query";
import axios from "axios";
import { ServerType } from "../types/server";

const createServers = async (data: ServerType, token: string) => {
  const url = `${process.env.REACT_APP_BASE_URL}/server/api/v1/server`;

  try {
    const response = await axios({
      method: "post",
      url,
      data: {
        serverName: data.name,
        userId: data.userId,
        hostName: data.hostName,
        theme: data.theme?.id,
        categories: data.categories?.map((category) => category.id),
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
