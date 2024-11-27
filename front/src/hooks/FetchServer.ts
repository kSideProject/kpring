import { useQuery } from "@tanstack/react-query";
import axios from "axios";
import { FetchedServerType } from "../types/server";

// 서버데이터 가져오기
export const fetchServers = async (token: string | null) => {
  const url = `${process.env.REACT_APP_BASE_URL}/server/api/v1/server`;

  try {
    const response = await axios({
      method: "get",
      url,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log(response.data);
    return response.data as FetchedServerType;
  } catch (error) {
    console.error(error);
  }
};

// const useFetchServers = (token: string | null) => {
//   return useQuery({
//     queryKey: ["servers"],
//     queryFn: () => {
//       if (token === null) {
//         return Promise.reject(new Error("Token is null"));
//       }
//       return fetchServers(token);
//     },
//   });
// };

// export default useFetchServers;
