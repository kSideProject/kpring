import React from "react";
import ServerCard from "./ServerCard";
import { useFilterFavorites } from "../../hooks/FavoriteServer";

const ServerCardList = ({
  servers,
}: {
  servers: {
    serverId: string;
    serverName: string;
    image: string;
    members: string[];
  }[];
}) => {
  const favoriteItems = useFilterFavorites();
  return (
    <>
      {servers
        .filter((server) => favoriteItems.includes(server.serverId))
        .map((server) => (
          <ServerCard key={server.serverId} servers={server} />
        ))}
    </>
  );
};

export default ServerCardList;
