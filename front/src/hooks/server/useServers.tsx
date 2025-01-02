import { getServers } from "@/api/server";
import { useLoginStore } from "@/store/useLoginStore";
import { GetServerType } from "@/types/server";
import { useEffect, useState } from "react";

const useServers = () => {
  const { accessToken } = useLoginStore();
  const [servers, setServers] = useState<GetServerType[] | null>(null);
  const [bookmarkedServers, setBookmarkedServers] = useState<
    GetServerType[] | undefined
  >(undefined);

  useEffect(() => {
    const fetchServer = async () => {
      try {
        const response = await getServers(accessToken);
        if (response) {
          setServers(response.data);

          const filteredServers = servers?.filter(
            (servers) => servers.bookmarked
          );

          setBookmarkedServers(filteredServers);
        }
      } catch (error) {
        throw new Error("");
      }
    };
    fetchServer();
  }, []);
  return { servers, bookmarkedServers };
};

export default useServers;
