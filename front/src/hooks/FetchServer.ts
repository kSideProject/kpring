import axios from "axios";

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

    return response;
  } catch (error) {
    console.log(error);
  }
};
